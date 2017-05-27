package controllers

import (
	"net/http"
)

func NewDrillController(w http.ResponseWriter, r *http.Request) {
	if r.FormValue("submit") != "" {
		w.Write([]byte("working"))
	}
}
