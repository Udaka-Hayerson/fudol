package handlers

import (
	"context"
	"fmt"
	"fudol_api/models"
	"fudol_api/schemas"
	"log"
	"net/http"

	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
)

type (
	TodoList struct {
		Handler
	}

	TodoCreateDTO struct {
		ID          schemas.TodoID `json:"id" validate:"required"` // new todo ID
		Title       string         `json:"title" validate:"required"`
		Description string         `json:"description" validate:"required"`
		ParentID    schemas.TodoID `json:"parentID"` // todo parent ID
	}

	TodoSetCompletedDTO struct {
		ID          schemas.TodoID `json:"id" validate:"required"` // todo ID
		IsCompleted bool           `json:"isCompleted" validate:"required"`
	}

	TodoRemoveDTO struct {
		ID schemas.TodoID `json:"id" validate:"required"` // todo ID
	}
)

// OpenAPi
//
//	@Tags		TodoList
//	@Summary	add todo
//	@Accept		json
//	@Produce	json
//	@Param		request	body	TodoCreateDTO	true	"body request"
//	@Security	ApiKeyAuth
//	@Success	201
//	@Failure	400	{object}	error	"invalid body fields."
//	@Failure	406	{object}	error	"parent ID is not esisted / ID is already esists."
//	@Router		/todo [post]
func (h *TodoList) PostTodo(c echo.Context) error {
	var b TodoCreateDTO

	if err := c.Bind(&b); err != nil {
		fmt.Println(err)
		return c.String(http.StatusBadRequest, "Invalid body fields")
	}

	if err := c.Validate(b); err != nil {
		return err
	}

	claims := h.GetTokenClaims(c)
	newTodo := schemas.Todo{
		ID:          b.ID,
		Title:       b.Title,
		Description: b.Description,
		UserID:      claims.User_id,
		ParentID:    b.ParentID,
	}

	if newTodo.ParentID != 0 {
		if res := h.Store.TodoLists.FindOne(context.TODO(), bson.M{"_id": b.ParentID}); res.Err() != nil {
			return c.String(http.StatusNotAcceptable, "parent id ist not exists")
		}
	}

	if _, err := h.Store.TodoLists.InsertOne(context.TODO(), newTodo); err != nil {
		fmt.Println(err)
		return c.String(http.StatusNotAcceptable, "id is alreadt exists")
	}

	return c.NoContent(http.StatusOK)
}

// OpenAPi
//
//	@Tags		TodoList
//	@Summary	set completed state
//	@Accept		json
//	@Produce	json
//	@Security	ApiKeyAuth
//	@Param		request	body	TodoSetCompletedDTO	true	"body request"
//	@Success	200
//	@Failure	400	{object}	error	"invalid body fields."
//	@Failure	406	{object}	error	"todo id doesn't exist / this todo belongs to another user"
//	@Router		/todo/completed [patch]
func (h *TodoList) SetCompleted(c echo.Context) error {
	var b TodoSetCompletedDTO

	if err := c.Bind(&b); err != nil {
		fmt.Println(err)
		return c.String(http.StatusBadRequest, "Invalid body fields")
	}

	if err := c.Validate(b); err != nil {
		return c.String(http.StatusBadRequest, err.Error())
	}

	claims := h.GetTokenClaims(c)
	res, err := h.Store.TodoLists.UpdateOne(
		context.TODO(),
		bson.D{
			{Key: "_id", Value: b.ID},
			{Key: "userID", Value: claims.User_id},
		},
		bson.D{{
			Key:   "$set",
			Value: bson.D{{Key: "isCompleted", Value: b.IsCompleted}},
		}},
	)

	if err != nil {
		log.Fatal(err)
	}

	if res.MatchedCount == 0 || res.ModifiedCount == 0 {
		return c.String(http.StatusNotAcceptable, "todo id doesn't exist or this todo belongs to another user")
	}

	return c.NoContent(http.StatusOK)
}

// OpenAPi
//
//	@Tags		TodoList
//	@Summary	get todo list
//	@Accept		json
//	@Produce	json
//	@Security	ApiKeyAuth
//	@Success	200	{array}	models.Todo
//	@Router		/todo [get]
func (h *TodoList) GetTodoList(c echo.Context) error {
	claims := h.GetTokenClaims(c)
	cursor, err := h.Store.TodoLists.Aggregate(
		context.TODO(),
		mongo.Pipeline{
			{{
				Key: "$match",
				Value: bson.D{
					{Key: "userID", Value: claims.User_id},
					{
						Key:   "parentID",
						Value: bson.D{{Key: "$exists", Value: false}},
					},
				},
			}},
			{{
				Key: "$lookup",
				Value: bson.D{
					{Key: "from", Value: "todos"},
					{Key: "localField", Value: "_id"},
					{Key: "foreignField", Value: "parentID"},
					{Key: "as", Value: "list"},
				},
			}},
		},
	)

	if err != nil {
		log.Fatal(err)
	}

	defer cursor.Close(context.TODO())

	var results []models.Todo

	if err = cursor.All(context.TODO(), &results); err != nil {
		log.Fatal(err)
	}

	return c.JSON(http.StatusOK, results)
}

// OpenAPi
//
//	@Tags		TodoList
//	@Summary	remove todo
//	@Accept		json
//	@Produce	json
//	@Security	ApiKeyAuth
//	@Param		request	body	TodoRemoveDTO	false	"todo's ID"
//	@Success	200
//	@Failure	400	{object}	error	"invalid request body"
//	@Router		/todo [delete]
func (h *TodoList) RemoveTodo(c echo.Context) error {
	var b TodoRemoveDTO

	if err := c.Bind(&b); err != nil {
		return c.String(http.StatusBadRequest, "invalid request body")
	}

	if err := c.Validate(b); err != nil {
		return c.String(http.StatusBadRequest, "invalid request body")
	}

	claims := h.GetTokenClaims(c)

	_, err := h.Store.TodoLists.DeleteMany(
		context.TODO(),
		bson.D{
			{Key: "userID", Value: claims.User_id},
			{Key: "$or", Value: bson.A{
				bson.D{{Key: "_id", Value: b.ID}},
				bson.D{{Key: "parentID", Value: b.ID}},
			}},
		},
	)

	if err != nil {
		return c.NoContent(http.StatusInternalServerError)
	}

	return c.NoContent(http.StatusOK)
}
