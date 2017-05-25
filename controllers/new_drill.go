package controllers

import (
	"net/http"
)

func NewDrillController(w http.ResponseWriter, r *http.Request) {
	w.Write([]byte("working"))
}
