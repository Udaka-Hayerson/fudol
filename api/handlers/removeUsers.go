package handlers

import (
	"context"
	"fmt"
	"net/http"
	"strings"

	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

func (h *Handler) RemoveUsers(c echo.Context) error {
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
