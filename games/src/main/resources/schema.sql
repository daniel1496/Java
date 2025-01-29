create table games (
    id bigint generated by default as identity,
    home_team varchar,
    away_team varchar,
    home_team_score int,
    away_team_score int,
    game_date timestamp,
    primary key(id)
);