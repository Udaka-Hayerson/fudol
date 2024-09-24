package helpers

import (
	"github.com/golang-jwt/jwt/v5"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type (
	JwtCustomClaims struct {
		Role    role
		User_id primitive.ObjectID
		jwt.RegisteredClaims
	}

	role uint8
)

const (
	Admin_role role = 0
	User_role  role = 1
)
