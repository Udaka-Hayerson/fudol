package dto

import "go.mongodb.org/mongo-driver/bson/primitive"

type (
	UserCreateDTO struct {
		Nickname        string  `json:"nickname" validate:"required"`
		Birthday        string  `json:"birthday"`
		Login           string  `json:"login" validate:"required"`
		Password        string  `json:"password" validate:"required"`
		Expected_salary float64 `json:"expected_salary" validate:"required"`
	}
	UserCreatedDTO struct {
		Id    primitive.ObjectID
		Token string
	}
)
