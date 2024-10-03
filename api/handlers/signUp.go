package handlers

import (
	"context"
	"fmt"
	dto "fudol_api/DTO"
	"fudol_api/helpers"
	"net/http"
	"os"
	"time"

	"github.com/golang-jwt/jwt/v5"
	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
)

// OpenAPi
//
//	@Tags		Auth
//	@Summary	sign up
//	@Accept		json
//	@Produce	json
//	@Success	201	{object}	dto.UserCreatedDTO
//	@Failure	400	{object}	error	"invalid body fields."
//	@Failure	406	{object}	error	"nickname or login is existed."
//	@Router		/sigup [post]
func (h *Handler) SignUp(c echo.Context) error {
	var u dto.UserCreateDTO
	err := c.Bind(&u)

	if err != nil {
		fmt.Println(err)
		return c.String(http.StatusBadRequest, "Invalid body fields")
	}

	if err = c.Validate(u); err != nil {
		return err
	}

	if a := h.Store.Users.FindOne(context.TODO(), bson.M{"nickname": u.Nickname}); a.Err() != mongo.ErrNoDocuments {
		return c.String(http.StatusNotAcceptable, "nickname is already exsited.")
	}

	if a := h.Store.Users.FindOne(context.TODO(), bson.M{"login": u.Login}); a.Err() != mongo.ErrNoDocuments {
		return c.String(http.StatusNotAcceptable, "login is already exsited.")
	}

	r, err := h.Store.Users.InsertOne(context.TODO(), u)

	if err != nil {
		return c.String(http.StatusInternalServerError, "Err while adding new user to DB.")
	}

	t, err := jwt.NewWithClaims(
		jwt.SigningMethodHS256,
		helpers.JwtCustomClaims{
			Role:    helpers.User_role,
			User_id: r.InsertedID.(primitive.ObjectID),
			RegisteredClaims: jwt.RegisteredClaims{
				ExpiresAt: jwt.NewNumericDate(time.Now().Add(time.Hour * 72)),
			},
		},
	).SignedString([]byte(os.Getenv("TOKEN_KEY")))

	if err != nil {
		return fmt.Errorf("err during creating token %v", err)
	}

	res := dto.UserCreatedDTO{Token: t}

	return c.JSON(http.StatusCreated, res)
}
