package models

import "go.mongodb.org/mongo-driver/bson/primitive"

type FudolPublic struct {
	TimeCount int64 `json:"timeCount"`
}

type Fudol struct {
	FudolPublic `bson:",inline"`
	UserID      primitive.ObjectID `json:"userID"`
}

type FudolRatingUser struct {
	ID       primitive.ObjectID `bson:"_id" json:"id"`
	Nickname string             `bson:"nickname" json:"nickname"`
}

type FudolRating struct {
	TimeCount uint64          `bson:"timeCount" json:"timeCount"`
	User      FudolRatingUser `json:"user"`
}
