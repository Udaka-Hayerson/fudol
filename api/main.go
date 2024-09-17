package main

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"
)

type User struct {
	Id              int
	Nickname        string
	Birthday        string
	Login           string
	Password        string
	Token           string
	Expected_salary float64
}

func main() {
	http.HandleFunc("/", echo)
	http.ListenAndServe(":8080", nil)
}

func echo(res http.ResponseWriter, req *http.Request) {
	fmt.Println(res, req)

	b, err := io.ReadAll(req.Body)

	if err != nil {
		fmt.Printf("%v", err)
	}

	fmt.Printf("%s", b)

	jData, _ := json.Marshal(User{
		Id:              234324,
		Nickname:        "Дубов",
		Birthday:        "давно",
		Login:           "дай пилу",
		Password:        "пiська. ха-ха",
		Token:           "make Pikula great again",
		Expected_salary: 666.6,
	})

	res.WriteHeader(201)
	res.Header().Set("Content-Type", "application/json")
	res.Write(jData)

	fmt.Println("end")
}
