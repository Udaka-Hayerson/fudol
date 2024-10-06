package models

type User struct {
	Id              string  `bson:"_id" json:"id"`
	Nickname        string  `bson:"nickname,omitempty" json:"nickname"`
	Birthday        string  `bson:"birthday,omitempty" json:"birthday"`
	Login           string  `bson:"login,omitempty" json:"login"`
	Password        string  `bson:"password,omitempty" json:"password"`
	Expected_salary float64 `bson:"expected_salary,omitempty" json:"expectedSalary"`
}
