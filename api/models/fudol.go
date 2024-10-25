package models

import "go.mongodb.org/mongo-driver/bson/primitive"

type FudolPublic struct {
	TimeCount int64 `json:"timeCount"`
}

type Fudol struct {
	FudolPublic
	UserID primitive.ObjectID `json:"userID"`
}
