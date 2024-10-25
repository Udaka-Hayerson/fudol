package handlers

// type Users struct {
// 	Handler
// }

// func (h *Handler) GetUserList(c echo.Context) error {
// 	cur, err := h.Store.Users.Find(context.TODO(), bson.D{})

// 	if err != nil {
// 		panic(err)
// 	}

// 	users := make([]models.User, 0)

// 	if err := cur.All(context.TODO(), &users); err != nil {
// 		c.Error(err)
// 	}

// 	return c.JSON(http.StatusOK, users)
// }

// OpenAPi
//
//	@Tags		User
//	@Summary	get user list
//	@Produce	json
//	@Success	200	{array}	models.UserPublic
//	@Router		/users [get]
// func (h *Users) GetUserList(c echo.Context) error {
// 	cur, err := h.Store.Users.Find(context.TODO(), bson.D{})

// 	if err != nil {
// 		panic(err)
// 	}

// 	users := make([]schemas.User, 0)

// 	if err := cur.All(context.TODO(), &users); err != nil {
// 		c.Error(err)
// 	}

// 	return c.JSON(http.StatusOK, users)
// }
