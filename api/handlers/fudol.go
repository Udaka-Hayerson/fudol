package handlers

import (
	"context"
	"fmt"
	"net/http"

	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo/options"
)

type (
	Fudol struct {
		Handler
	}

	IncreaseTimeCountDTO struct {
		Count int64 `json:"count" validate:"required"`
	}
)

// OpenAPi
//
//	@Tags		Fudol
//	@Summary	Increase time count
//	@Security	ApiKeyAuth
//	@Accept		json
//	@Produce	json
//	@Header		200		{string}	Token					"Bearer"
//	@Param		request	body		IncreaseTimeCountDTO	true	"body request"
//	@Success	200
//	@Failure	400	{object}	error	"invalid body fields."
//	@Router		/fudol/increase [patch]
func (h *Handler) TimeCountIncrease(c echo.Context) error {
	var b IncreaseTimeCountDTO

	if err := c.Bind(&b); err != nil {
		c.String(http.StatusBadRequest, "invalid fields.")
	}

	if err := c.Validate(b); err != nil {
		return err
	}

	claims := h.GetTokenClaims(c)
	_, err := h.Store.Fudols.UpdateOne(
		context.TODO(),
		bson.M{"UserID": claims.User_id},
		bson.M{"$inc": bson.M{"timeCount": b.Count}},
		options.Update().SetUpsert(true),
	)

	if err != nil {
		fmt.Println(err)
		c.String(http.StatusInternalServerError, "error during increasing time count")
	}

	return c.NoContent(http.StatusOK)
}

// OpenAPi
//
//	@Tags		Fudol
//	@Summary	Reset time count
//	@Security	ApiKeyAuth
//	@Accept		json
//	@Produce	json
//	@Header		200	{string}	Token	"Bearer"
//	@Success	200
//	@Router		/fudol/reset [patch]
func (h *Handler) TimeCountReset(c echo.Context) error {
	claims := h.GetTokenClaims(c)
	_, err := h.Store.Fudols.UpdateOne(
		context.TODO(),
		bson.M{"UserID": claims.User_id},
		bson.M{"$set": bson.D{{Key: "timeCount", Value: 0}}},
	)

	if err != nil {
		fmt.Println(err)
		c.String(http.StatusInternalServerError, "error during reseting time count")
	}

	return c.NoContent(http.StatusOK)
}
