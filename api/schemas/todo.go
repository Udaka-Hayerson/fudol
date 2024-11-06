package schemas

import "go.mongodb.org/mongo-driver/bson/primitive"

type TodoID uint64

type Todo struct {
	ID          TodoID             `bson:"_id" json:"id"`
	Title       string             `bson:"title" json:"title"`
	Description string             `bson:"description" json:"description"`
	ParentID    TodoID             `bson:"parentID,omitempty" json:"parentID"`
	UserID      primitive.ObjectID `bson:"userID" json:"userID"`
	IsCompleted bool               `bson:"isCompleted" json:"isCompleted"`
}
