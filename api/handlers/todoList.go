package handlers

import (
	"context"
	"fmt"
	"fudol_api/models"
	"fudol_api/schemas"
	"log"
	"net/http"
	"strconv"

	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
)

type (
	TodoList struct {
		Handler
	}

	TodoCreateDTO struct {
		ID          schemas.TodoID `json:"id" validate:"required"`
		Title       string         `json:"title" validate:"required"`
		Description string         `json:"description" validate:"required"`
		ParentID    schemas.TodoID `json:"parentID"`
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
//	@Header		200	{string}	Token	"Bearer"
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
//	@Summary	get todo list
//	@Accept		json
//	@Produce	json
//	@Security	ApiKeyAuth
//	@Header		200	{string}	Token	"Bearer"
//	@Success	200	{array}		models.Todo
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
//	@Param		id	query		string	false	"todo's ID"
//	@Header		200	{string}	Token	"Bearer"
//	@Success	200
//	@Failure	400	{object}	error	"id param is not provided"
//	@Router		/todo [delete]
func (h *TodoList) RemoveTodo(c echo.Context) error {
	claims := h.GetTokenClaims(c)
	idParam := c.QueryParam("id")

	if idParam == "" {
		return c.String(http.StatusBadRequest, "id param is not provided")
	}

	id, errAtoi := strconv.Atoi(idParam)

	if errAtoi != nil {
		return c.NoContent(http.StatusInternalServerError)
	}

	_, err := h.Store.TodoLists.DeleteMany(
		context.TODO(),
		bson.D{
			{Key: "userID", Value: claims.User_id},
			{Key: "$or", Value: bson.A{
				bson.D{{Key: "_id", Value: id}},
				bson.D{{Key: "parentID", Value: id}},
			}},
		},
	)

	if err != nil {
		return c.NoContent(http.StatusInternalServerError)
	}

	return c.NoContent(http.StatusOK)
}
