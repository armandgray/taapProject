package main_test

import (
	"testing"
	"net/http"

  gmux "github.com/gorilla/mux"
)

var m *gmux.Router
var req *http.Request
var err error

func setup() {
	m = gmux.NewRouter()
}

func TestGet400OnNewDrillRoute(t *testing.T) {
	setup()

	req, err = http.NewRequest("POST", "/drills/new", nil)
	if err != nil {
    t.Fatal("Creating 'POST /questions/1/SC' request failed!")
  }
}