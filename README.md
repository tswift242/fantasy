Fantasy Football Custom Scoring Helper
======================================

This application is designed to help fantasy football league comissioners choose approperiate custom scoring rules for their league, should they desire to stray from the defaults. By using the program, one can see what the effects of a specific set of custom rules are, both on the absolute scores of each player, and the relative rankings of the players.

## About the program
The program allows one to choose a set of custom scoring rules for the league by assigning a scoring rule to each of the supported scoring statistics. Then, one can have these rules take effect by evaluating each player against the scoring rules, which assigns each player an updated score. Players are automatically sorted by score so that relative rankings can easily be discerned. 

For convenience, players are organized by position. Selecting a player mode changes which players are currently visible based on position. Only players corresponding to the current player mode are scored against the current set of scoring rules.

The application allows one to score the same set of players against 2 different sets of scoring rules, to allow easy comparisons between slight (or significant) rule variations. We call the area within the UI containing a single set of players with their accompanying scoring rules a "scoring panel". The program's UI consists of 2 scoring panels, as well as panel which specifies global options across both scoring panels.

## Features/Functionality

### Global options/actions
These are options which apply to both scoring panels.

* **Player mode:** The player mode determines which players are currently displayed, and correspond to football positions. Only players belonging to the current mode are scored upon clicking the "Recalculate scores" button.
    * Default mode: QB
    * Note: the "All" mode is currently **not** functional.
* **League site:** A league site may be chosen, which determines what the default scoring rules are. Supported league sites are: NFL, ESPN, Yahoo, and CBS.
    * Default site: NFL
    * Note: changing the league site automatically resets the all scoring rules to the defaults of that fantasy league site.
* **Recalculate scores:** Clicking this button causes players to be scored using the current set of scoring rules.
    * Note: Players are **NOT** automatically re-scored in response to any other action or event. This includes changing the player mode, or updating the scoring rules. All displayed player scores are considered stale until this button is pressed.

### Individual options/actions
These are options which apply to each individual scoring panel.

* **Scoring rules:** Assign a scoring rule to each of the supported statistics in the format of `points/unit`, which will award `points`-many points for each `unit`-many of the given statistic recorded for each player.
    * E.g. The scoring rule `RUSH_YDS: 1.5/10` would award 1.5 points for every 10 rushing yards.
    * Note: `points` may be any decimal (floating point) number (positive or negative), while `unit` must be an integer.
* Supported statistics include (but are not limited to): passing/rushing/receiving/defensive yards and touchdowns, made and missed field goals by distance, turnovers, and sacks. See the "Stat Abbreviations" section below for clarification of any confusing statistical abbreviations used by the program.
* **Restore default rules:** All scoring rules can be easily reset back to their defaults by clicking the "Restore default rules" button.
* **Sorting:** Playes may be manually sorted by any of the relevant statistical categories for that group of players, as well as by player name. By default, players are sorted automatically by score in ascending order.

## Stat Abbreviations

### Multi-categorical
* YDS = Yards
* TDS = Touchdowns
* INT = Interceptions
* SCK = Sacks

### Passing
* PASS = Passing
* COMP = Completions
* INC = Incompletions

### Rushing
* RUSH = Rushing
* ATT = Attempts

### Receiving
* REC = Receiving / Receptions

### General Offense
* FUMB = Fumbles (not lost)
* FUMB_LOST = Fumbles lost
* FUMB_TD = Fumbles recovered for a touchdown
* 2_PT_CONV = 2 point conversion

### Kicking
* PAT = Point After Touchdown
* FG = Field Goal
* MD = Made
* MS = Missed
* PAT/FG_MD/MS_0 = PAT/FG made/missed from 0-19 yards
* PAT/FG_MD/MS_20 = PAT/FG made/missed from 20-29 yards
* PAT/FG_MD/MS_30 = PAT/FG made/missed from 30-39 yards
* PAT/FG_MD/MS_40 = PAT/FG made/missed from 40-49 yards
* PAT/FG_MD/MS_50 = PAT/FG made/missed from 50+ yards

### Defensive
* DEF = Defensive
* FUMB_REC = Fumbles recovered
* SAF = Safeties
* RET = Return touchdowns (punt and kickoff).