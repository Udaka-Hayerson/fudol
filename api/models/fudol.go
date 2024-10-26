package models

import "go.mongodb.org/mongo-driver/bson/primitive"

type FudolPublic struct {
	TimeCount int64 `json:"timeCount"`
}

type Fudol struct {
	FudolPublic
	UserID primitive.ObjectID `json:"userID"`
}

// type FudolRating struct {
// 	TimeCount uint64
// 	User      struct {
// 		ID       primitive.ObjectID `bson:"_id" json:"id"`
// 		Nickname string             `bson:"nickname" json:"nickname"`
// 		Birthday string             `json:"birthday"`
// 	}
// }
