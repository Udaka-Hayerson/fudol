package models

import "fudol_api/schemas"

type Todo struct {
	ID          int            `bson:"_id" json:"id"`
	ParentID    schemas.TodoID `bson:"parentID,omitempty" json:"parentID"`
	Title       string         `json:"title"`
	Description string         `json:"description"`
	List        []Todo         `json:"list"`
	IsCompleted bool           `json:"isCompleted"`
}
