package handlers

import (
	"context"
	"fmt"
	"fudol_api/dto"
	"fudol_api/helpers"
	"fudol_api/models"
	"net/http"

	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/bson"
)

type UserSignInDTO struct {
	Login    string `json:"login" validate:"required"`
	Password string `json:"password" validate:"required"`
}

// OpenAPi
//
//	@Tags		Auth
//	@Summary	sign in
//	@Accept		json
//	@Produce	json
//	@Param		request	body		UserSignInDTO	true	"body request"
//	@Success	200		{object}	dto.SuccessAuthDTO
//	@Failure	400		{object}	error	"invalid body fields."
//	@Failure	406		{object}	error	"Invalid credentials."
//	@Router		/signin [post]
func (h *Handler) SignIn(c echo.Context) error {
	var b UserSignInDTO

	if err := c.Bind(&b); err != nil {
		fmt.Println(err)
		return c.String(http.StatusBadRequest, "Invalid body fields")
	}

	if err := c.Validate(b); err != nil {
		return err
	}

	a := h.Store.Users.FindOne(context.TODO(), bson.M{"login": b.Login, "password": b.Password})

	if a.Err() != nil {
		return c.String(http.StatusNotAcceptable, "Invalid credentials.")
	}

	var u models.User
	a.Decode(&u)

	t, err := h.gentoken(u.Id, helpers.User_role)

	if err != nil {
		return fmt.Errorf("err during creating token %v", err)
	}

	res := dto.SuccessAuthDTO{Token: t}

	return c.JSON(http.StatusOK, res)
}
