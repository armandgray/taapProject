package models

type SessionLog struct {
	LogId         int     `db:"log_id"`
	Date          int     `db:"date"`
	Goal          string  `db:"goal"`
	ActiveWork    int     `db:"active_work"`
	RestTime      int     `db:"rest_time"`
	SetsCompleted int     `db:"sets_completed"`
	RepsCompleted int     `db:"reps_completed"`
	SuccessRate   float64 `db:"success_rate"`
	Drill         int     `db:"drill"`
}
