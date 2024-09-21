package models

type User struct {
	Id              int
	Nickname        int     `bson:"nickname,omitempty"`
	Birthday        string  `bson:"birthday,omitempty"`
	Login           string  `bson:"login,omitempty"`
	Password        string  `bson:"password,omitempty"`
	Expected_salary float64 `bson:"expectedSalary,omitempty"`
}
