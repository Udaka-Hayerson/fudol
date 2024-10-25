package schemas

import "go.mongodb.org/mongo-driver/bson/primitive"

type Fudol struct {
	UserID    primitive.ObjectID `bson:"userID" json:"userID"`
	TimeCount int64              `bson:"timeCount,omitempty" json:"timeCount"`
}
