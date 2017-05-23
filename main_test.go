package main_test

import (
	"testing"
	"net/http"
	"net/http/httptest"

  gmux "github.com/gorilla/mux"
)

var r *gmux.Router
var req *http.Request
var err error
var respRec *httptest.ResponseRecorder

func setup() {
	r = gmux.NewRouter()
	respRec = httptest.NewRecorder()
}

func TestGet400OnNewDrillRoute(t *testing.T) {
	setup()

	req, err = http.NewRequest("POST", "/drills/new", nil)
	if err != nil {
    t.Fatal("Creating 'POST /questions/1/SC' request failed!")
  }

  r.ServeHTTP(respRec, req)

  if respRec.Code != http.StatusBadRequest {
    t.Fatal("Server error: Returned ", respRec.Code, " instead of ", http.StatusBadRequest)
  }
}