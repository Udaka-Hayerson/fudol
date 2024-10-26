package handlers

import (
	"context"
	"fmt"
	"fudol_api/schemas"
	"net/http"
	"strings"

	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type Admin struct {
	Handler
}

func (h *Admin) GetUserList(c echo.Context) error {
	cur, err := h.Store.Users.Find(context.TODO(), bson.D{{}})

	if err != nil {
		panic(err)
	}

	users := make([]schemas.User, 0)

	if err := cur.All(context.TODO(), &users); err != nil {
		fmt.Println(err.Error())
		return c.NoContent(http.StatusInternalServerError)
	}

	return c.JSON(http.StatusOK, users)
}

func (h *Admin) RemoveUsers(c echo.Context) error {
	idsQ := c.QueryParam("ids")

	if idsQ == "" {
		return c.String(http.StatusBadRequest, "ids query param is not provided.")
	}

	idsS := strings.Split(idsQ, ",")
	var ids []primitive.ObjectID

	for _, v := range idsS {
		r, _ := primitive.ObjectIDFromHex(v)
		ids = append(ids, r)
	}

	_, err := h.Store.Users.DeleteMany(context.TODO(), bson.M{"_id": bson.M{"$in": ids}})

	if err != nil {
		return c.String(http.StatusInternalServerError, fmt.Sprintf("%v", err))
	}

	return c.NoContent(http.StatusOK)
}
