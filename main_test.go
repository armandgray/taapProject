package main_test

import (
	"testing"
	"net/http"
)

func TestGet400OnNewDrillRoute(t *testing.T) {
	_, err := http.NewRequest("GET", "/drills/new", nil)
	if err != nil {
    t.Fatal("Creating 'GET /questions/1/SC' request failed!")
  }
}