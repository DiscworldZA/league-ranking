# League Ranking


A simple league ranking system written for SPAN Digital

Provided a list of games the system will calculate the points for each team

## League Rules

- Winning is worth 3 Points for the winning team
- Tying is worth 1 Point for the both teams
- Losing is worth 0 Points for the losing team


## Sample
### Sample Input

~~~~
Lions 3, Snakes 3
Tarantulas 1, FC Awesome 0
Lions 1, FC Awesome 1
Tarantulas 3, Snakes 1
Lions 4, Grouches 0
~~~~

### Sample Output

~~~~
1. Tarantulas, 6 pts
2. Lions, 5 pts
3. FC Awesome, 1 pt
4. Snakes, 1 pt
5. Grouches, 0 pts
~~~~

## Instructions

1. Add score file to `scores` directory
2. Use `sbt run` to run
