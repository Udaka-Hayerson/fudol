package handlers

import (
	"context"
	"fmt"
	"fudol_api/models"
	"log"
	"net/http"

	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
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
//	@Summary	get fudol
//	@Security	ApiKeyAuth
//	@Accept		json
//	@Success	200	{object}	models.FudolPublic
//	@Failure	400	{object}	error	"user doesn't have a fudols yet"
//	@Router		/fudol [get]
func (h *Handler) GetFudol(c echo.Context) error {
	claims := h.GetTokenClaims(c)

	res := h.Store.Fudols.FindOne(context.TODO(), bson.D{{Key: "userID", Value: claims.User_id}})
	var f models.FudolPublic

	if err := res.Decode(&f); err != nil {
		fmt.Println(err)
		return c.NoContent(http.StatusNotFound)
	}

	return c.JSON(http.StatusOK, f)
}

// OpenAPi
//
//	@Tags		Fudol
//	@Summary	increase time count
//	@Security	ApiKeyAuth
//	@Accept		json
//	@Produce	json
//	@Param		request	body	IncreaseTimeCountDTO	true	"body request"
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
		bson.M{"userID": claims.User_id},
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
//	@Summary	reset time count
//	@Security	ApiKeyAuth
//	@Accept		json
//	@Produce	json
//	@Success	200
//	@Router		/fudol/reset [patch]
func (h *Handler) TimeCountReset(c echo.Context) error {
	claims := h.GetTokenClaims(c)
	_, err := h.Store.Fudols.UpdateOne(
		context.TODO(),
		bson.M{"userID": claims.User_id},
		bson.M{"$set": bson.D{{Key: "timeCount", Value: 0}}},
	)

	if err != nil {
		fmt.Println(err)
		c.String(http.StatusInternalServerError, "error during reseting time count")
	}

	return c.NoContent(http.StatusOK)
}

// OpenAPi
//
//	@Tags		Fudol
//	@Summary	user rating list
//	@Security	ApiKeyAuth
//	@Accept		json
//	@Produce	json
//	@Success	200	{array}	models.FudolRating
//	@Router		/fudol/users [get]
func (h *Handler) UserRatingList(c echo.Context) error {
	cursor, err := h.Store.Fudols.Aggregate(
		context.TODO(),
		mongo.Pipeline{
			bson.D{{Key: "$match", Value: bson.D{{}}}},
			{{
				Key: "$lookup",
				Value: bson.D{
					{Key: "from", Value: "users"},
					{Key: "localField", Value: "userID"},
					{Key: "foreignField", Value: "_id"},
					{Key: "as", Value: "user"},
				},
			}},
			{{
				Key: "$unwind",
				Value: bson.D{
					{Key: "path", Value: "$user"},
					{Key: "preserveNullAndEmptyArrays", Value: true},
				},
			}},
		},
	)

	if err != nil {
		fmt.Println(err)
		c.String(http.StatusInternalServerError, "error during increasing time count")
	}

	defer cursor.Close(context.TODO())

	var results []models.FudolRating

	if err = cursor.All(context.TODO(), &results); err != nil {
		log.Fatal(err)
	}

	return c.JSON(http.StatusOK, results)
}
