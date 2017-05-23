package main_test

import (
	"taap_project/routes"

	"testing"
	"net/http"
	"net/http/httptest"

  gmux "github.com/gorilla/mux"
)

var mux *gmux.Router
var req *http.Request
var err error
var respRec *httptest.ResponseRecorder

func setup() {
	mux = gmux.NewRouter()
	respRec = httptest.NewRecorder()
}

func TestGet200OnNewDrillRoute(t *testing.T) {
	setup()
	routes.NewDrillRoute(mux)

	req, err = http.NewRequest("GET", "/taap/api/drills/new", nil)
	if err != nil {
    t.Fatal("Creating 'GET /taap/api/drills/new' request failed!")
  }

  mux.ServeHTTP(respRec, req)

  if respRec.Code != http.StatusOK {
    t.Fatal("Server error: Returned ", respRec.Code, " instead of ", http.StatusOK)
  }
}