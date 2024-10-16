package handlers

import (
	"context"
	"fmt"
	"fudol_api/constants"
	"fudol_api/helpers"
	"net/http"

	"github.com/golang-jwt/jwt/v5"
	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/bson"
)

type (
	IncreaseTimeCountDTO struct {
		Count int64 `json:"count" validate:"required"`
	}
)

// OpenAPi
//
//	@Tags		Time count
//	@Summary	Increase time count
//	@Security	ApiKeyAuth
//	@Accept		json
//	@Produce	json
//	@Header		200		{string}	Token					"Bearer"
//	@Param		request	body		IncreaseTimeCountDTO	true	"body request"
//	@Success	200
//	@Failure	400	{object}	error	"invalid body fields."
//	@Router		/timecount/increase [patch]
func (h *Handler) TimeCountIncrease(c echo.Context) error {
	var b IncreaseTimeCountDTO

	if err := c.Bind(&b); err != nil {
		c.String(http.StatusBadRequest, "invalid fields.")
	}

	if err := c.Validate(b); err != nil {
		return err
	}

	token, _ := c.Get(constants.TokenData).(*jwt.Token)
	claims, _ := token.Claims.(*helpers.JwtCustomClaims)

	res, err := h.Store.Users.UpdateByID(
		context.TODO(),
		claims.User_id,
		bson.M{"$inc": bson.D{{Key: "time_count", Value: b.Count}}},
	)

	if err != nil {
		fmt.Println(err)
		c.String(http.StatusInternalServerError, "error during increasing time count")
	}

	if res.MatchedCount == 0 {
		c.String(http.StatusInternalServerError, "no matched users by id.")
	}

	return c.NoContent(http.StatusOK)
}
