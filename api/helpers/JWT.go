package helpers

import (
	"github.com/golang-jwt/jwt/v5"
)

type (
	JwtCustomClaims struct {
		Role    Role
		User_id string
		jwt.RegisteredClaims
	}

	Role uint8
)

const (
	Admin_role Role = 0
	User_role  Role = 1
)
