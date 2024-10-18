package models

import "go.mongodb.org/mongo-driver/bson/primitive"

type UserPublic struct {
	Id              primitive.ObjectID `bson:"_id" json:"id"`
	Nickname        string             `bson:"nickname,omitempty" json:"nickname"`
	Birthday        string             `bson:"birthday,omitempty" json:"birthday"`
	Expected_salary float64            `bson:"expected_salary,omitempty" json:"expectedSalary"`
	TimeCount       int64              `bson:"time_count,omitempty" json:"timeCount"`
}

type User struct {
	UserPublic `bson:",inline"`
	Login      string `bson:"login,omitempty" json:"login"`
	Password   string `bson:"password,omitempty" json:"password"`
}
