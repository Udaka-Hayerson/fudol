package models

import "go.mongodb.org/mongo-driver/bson/primitive"

type UserPublic struct {
	ID              primitive.ObjectID `bson:"_id" json:"id"`
	Nickname        string             `bson:"nickname" json:"nickname"`
	Birthday        string             `json:"birthday"`
	Expected_salary float64            `json:"expectedSalary"`
}

type UserPrivate struct {
	UserPublic `bson:",inline"`
	TodoList   []Todo `json:"todoList"`
	Fudol      Fudol  `json:"fudol"`
	Login      string `json:"login"`
	Password   string `json:"password"`
}
