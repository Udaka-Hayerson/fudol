package helpers

import (
	"github.com/golang-jwt/jwt/v5"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type (
	JwtCustomClaims struct {
		Role    Role
		User_id primitive.ObjectID
		jwt.RegisteredClaims
	}

	Role uint8
)

const (
	Admin_role Role = 0
	User_role  Role = 1
)
