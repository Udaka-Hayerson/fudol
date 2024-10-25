package schemas

import "go.mongodb.org/mongo-driver/bson/primitive"

type User struct {
	ID              primitive.ObjectID `bson:"_id" json:"id"`
	Nickname        string             `bson:"nickname,omitempty" json:"nickname"`
	Birthday        string             `bson:"birthday,omitempty" json:"birthday"`
	Expected_salary float64            `bson:"expectedSalary,omitempty" json:"expectedSalary"`
	Login           string             `bson:"login,omitempty" json:"login"`
	Password        string             `bson:"password,omitempty" json:"password"`
}
