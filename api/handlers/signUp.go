package handlers

import (
	"context"
	"fmt"
	"fudol_api/dto"
	"fudol_api/helpers"
	"net/http"

	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
)

type UserSignUpDTO struct {
	Nickname        string  `json:"nickname" validate:"required"`
	Birthday        string  `json:"birthday"`
	Login           string  `json:"login" validate:"required"`
	Password        string  `json:"password" validate:"required"`
	Expected_salary float64 `json:"expected_salary" validate:"required"`
}

// OpenAPi
//
//	@Tags		Auth
//	@Summary	sign up
//	@Accept		json
//	@Produce	json
//	@Param		request	body		UserSignUpDTO	true	"body request"
//	@Success	201		{object}	dto.SuccessAuthDTO
//	@Failure	400		{object}	error	"invalid body fields."
//	@Failure	406		{object}	error	"nickname or login is existed."
//	@Router		/signup [post]
func (h *Handler) SignUp(c echo.Context) error {
	var b UserSignUpDTO

	if err := c.Bind(&b); err != nil {
		fmt.Println(err)
		return c.String(http.StatusBadRequest, "Invalid body fields")
	}

	if err := c.Validate(b); err != nil {
		return err
	}

	if a := h.Store.Users.FindOne(context.TODO(), bson.M{"nickname": b.Nickname}); a.Err() != mongo.ErrNoDocuments {
		return c.String(http.StatusNotAcceptable, "nickname is already exsited.")
	}

	if a := h.Store.Users.FindOne(context.TODO(), bson.M{"login": b.Login}); a.Err() != mongo.ErrNoDocuments {
		return c.String(http.StatusNotAcceptable, "login is already exsited.")
	}

	r, err := h.Store.Users.InsertOne(context.TODO(), b)

	if err != nil {
		return c.String(http.StatusInternalServerError, "Err while adding new user to DB.")
	}

	t, err := h.gentoken(r.InsertedID.(primitive.ObjectID), helpers.User_role)

	if err != nil {
		return fmt.Errorf("err during creating token %v", err)
	}

	res := dto.SuccessAuthDTO{Token: t}

	return c.JSON(http.StatusCreated, res)
}
