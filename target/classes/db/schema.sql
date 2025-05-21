CREATE TABLE IF NOT EXISTS move (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(10) NOT NULL,
    damage INT NOT NULL,
    effect VARCHAR(10),
    effect_chance DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS monster (
    id SERIAL PRIMARY KEY,
    name VARCHAR(15) NOT NULL,
    health INT NOT NULL,
    attack INT NOT NULL
);

CREATE TABLE IF NOT EXISTS monster_move (
    monster_id INT REFERENCES monster(id) ON DELETE CASCADE,
    move_id INT REFERENCES move(id) ON DELETE CASCADE,
    PRIMARY KEY (monster_id, move_id)
);

CREATE TABLE IF NOT EXISTS team (
    id BOOLEAN PRIMARY KEY DEFAULT TRUE CHECK (id),
    monster1_id INT REFERENCES monster(id),
    monster2_id INT REFERENCES monster(id),
    monster3_id INT REFERENCES monster(id)
);