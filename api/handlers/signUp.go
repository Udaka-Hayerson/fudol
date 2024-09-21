package handlers

import (
	"context"
	"fmt"
	dto "fudol_api/DTO"
	"net/http"

	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
)

func (h *Handler) SignUp(c echo.Context) error {
	var u dto.UserCreateDTO
	err := c.Bind(&u)

	if err != nil {
		fmt.Println(err)
		return c.String(http.StatusBadRequest, "Bad request.")
	}

	if err = c.Validate(u); err != nil {
		return err
	}

	if a := h.Store.Users.FindOne(context.TODO(), bson.M{"nickname": u.Nickname}); a.Err() != mongo.ErrNoDocuments {
		return c.String(http.StatusBadRequest, "nickname is already exsited.")
	}

	if a := h.Store.Users.FindOne(context.TODO(), bson.M{"login": u.Login}); a.Err() != mongo.ErrNoDocuments {
		return c.String(http.StatusBadRequest, "login is already exsited.")
	}

	r, err := h.Store.Users.InsertOne(context.TODO(), u)

	if err != nil {
		c.Logger().Error(err)
		return c.String(http.StatusInternalServerError, "Err while adding new user to DB.")
	}

	res := dto.UserCreatedDTO{Id: r.InsertedID.(primitive.ObjectID), Token: "test_token"}

	return c.JSON(http.StatusCreated, res)
}
