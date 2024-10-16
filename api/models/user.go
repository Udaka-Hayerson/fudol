package models

import "go.mongodb.org/mongo-driver/bson/primitive"

type User struct {
	Id              primitive.ObjectID `bson:"_id" json:"id"`
	Nickname        string             `bson:"nickname,omitempty" json:"nickname"`
	Birthday        string             `bson:"birthday,omitempty" json:"birthday"`
	Login           string             `bson:"login,omitempty" json:"login"`
	Password        string             `bson:"password,omitempty" json:"password"`
	Expected_salary float64            `bson:"expected_salary,omitempty" json:"expectedSalary"`
	TimeCount       int64              `bson:"time_count,omitempty" json:"timeCount"`
}
