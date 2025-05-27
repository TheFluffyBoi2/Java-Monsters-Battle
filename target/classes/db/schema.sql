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

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM monster) THEN
        INSERT INTO monster (name, health, attack) VALUES
            ('Starter 1', 100, 50),
            ('Starter 2', 250, 20),
            ('Starter 3', 80, 80);
    END IF;
    IF NOT EXISTS (SELECT 1 FROM move) THEN
        INSERT INTO move (name, type, damage) VALUES
            ('Tackle', 'NORMAL', 40),
            ('Pound', 'NORMAL', 60);
    END IF;
    IF NOT EXISTS (SELECT 1 FROM monster_move) THEN
        INSERT INTO monster_move (monster_id, move_id) VALUES
            (1, 1),
            (1, 2),
            (2, 1),
            (2, 2),
            (3, 1),
            (3, 2);
    END IF;
    IF NOT EXISTS (SELECT 1 FROM team) THEN
        INSERT INTO team (monster1_id, monster2_id, monster3_id) VALUES
            (1, 2, 3);
    END IF;
END
$$;