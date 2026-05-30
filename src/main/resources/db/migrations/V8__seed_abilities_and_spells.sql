INSERT INTO dnd_editor.abilities
(
    ability_id,
    code,
    name
)
VALUES
    ('11111111-1111-4111-8111-111111111111', 'STRENGTH', 'Сила'),
    ('22222222-2222-4222-8222-222222222222', 'DEXTERITY', 'Ловкость'),
    ('33333333-3333-4333-8333-333333333333', 'CONSTITUTION', 'Телосложение'),
    ('44444444-4444-4444-8444-444444444444', 'INTELLIGENCE', 'Интеллект'),
    ('55555555-5555-4555-8555-555555555555', 'WISDOM', 'Мудрость'),
    ('66666666-6666-4666-8666-666666666666', 'CHARISMA', 'Харизма')
ON CONFLICT (ability_id) DO UPDATE SET
    code = EXCLUDED.code,
    name = EXCLUDED.name;

INSERT INTO dnd_editor.spells
(
    spell_id,
    spell_name,
    spell_level,
    spell_school,
    casting_time,
    spell_range,
    components,
    duration,
    spell_description
)
VALUES
    ('10000000-0000-4000-8000-000000000001', 'Малая иллюзия', 0, 'Иллюзия', '1 действие', '30 футов', 'С, М', '1 минута', 'Создаёт небольшой звук или образ.'),
    ('10000000-0000-4000-8000-000000000002', 'Свет', 0, 'Воплощение', '1 действие', 'Касание', 'В, М', '1 час', 'Заставляет предмет испускать яркий свет.'),
    ('10000000-0000-4000-8000-000000000003', 'Огненный снаряд', 0, 'Воплощение', '1 действие', '120 футов', 'В, С', 'Мгновенная', 'Метает огненный заряд в цель.'),
    ('10000000-0000-4000-8000-000000000004', 'Луч холода', 0, 'Воплощение', '1 действие', '60 футов', 'В, С', 'Мгновенная', 'Холодный луч наносит урон и замедляет цель.'),
    ('10000000-0000-4000-8000-000000000005', 'Громовая волна', 1, 'Воплощение', '1 действие', 'На себя', 'В, С', 'Мгновенная', 'Волна громовой силы отталкивает существ.'),
    ('10000000-0000-4000-8000-000000000006', 'Обнаружение магии', 1, 'Прорицание', '1 действие', 'На себя', 'В, С', 'Концентрация, до 10 минут', 'Позволяет ощущать присутствие магии.'),
    ('10000000-0000-4000-8000-000000000007', 'Лечение ран', 1, 'Воплощение', '1 действие', 'Касание', 'В, С', 'Мгновенная', 'Восстанавливает хиты существу.'),
    ('10000000-0000-4000-8000-000000000008', 'Благословение', 1, 'Очарование', '1 действие', '30 футов', 'В, С, М', 'Концентрация, до 1 минуты', 'Усиливает атаки и спасброски союзников.'),
    ('10000000-0000-4000-8000-000000000009', 'Волшебная стрела', 1, 'Воплощение', '1 действие', '120 футов', 'В, С', 'Мгновенная', 'Создаёт магические дротики, попадающие в цели.'),
    ('10000000-0000-4000-8000-000000000010', 'Щит', 1, 'Ограждение', '1 реакция', 'На себя', 'В, С', '1 раунд', 'Временно повышает класс доспеха.'),
    ('10000000-0000-4000-8000-000000000011', 'Туманный шаг', 2, 'Вызов', '1 бонусное действие', 'На себя', 'В', 'Мгновенная', 'Телепортирует на короткое расстояние.'),
    ('10000000-0000-4000-8000-000000000012', 'Невидимость', 2, 'Иллюзия', '1 действие', 'Касание', 'В, С, М', 'Концентрация, до 1 часа', 'Делает существо невидимым.'),
    ('10000000-0000-4000-8000-000000000013', 'Удержание личности', 2, 'Очарование', '1 действие', '60 футов', 'В, С, М', 'Концентрация, до 1 минуты', 'Парализует гуманоида при провале спасброска.'),
    ('10000000-0000-4000-8000-000000000014', 'Паутина', 2, 'Вызов', '1 действие', '60 футов', 'В, С, М', 'Концентрация, до 1 часа', 'Создаёт липкую область паутины.'),
    ('10000000-0000-4000-8000-000000000026', 'Духовное оружие', 2, 'Воплощение', '1 бонусное действие', '60 футов', 'В, С', '1 минута', 'Создаёт магическое оружие, атакующее врагов.'),
    ('10000000-0000-4000-8000-000000000015', 'Возрождение', 3, 'Некромантия', '1 действие', 'Касание', 'В, С, М', 'Мгновенная', 'Возвращает недавно умершее существо к жизни.'),
    ('10000000-0000-4000-8000-000000000016', 'Огненный шар', 3, 'Воплощение', '1 действие', '150 футов', 'В, С, М', 'Мгновенная', 'Взрыв огня наносит урон в области.'),
    ('10000000-0000-4000-8000-000000000017', 'Контрзаклинание', 3, 'Ограждение', '1 реакция', '60 футов', 'С', 'Мгновенная', 'Прерывает сотворение другого заклинания.'),
    ('10000000-0000-4000-8000-000000000018', 'Молния', 3, 'Воплощение', '1 действие', 'На себя', 'В, С, М', 'Мгновенная', 'Линия молнии поражает существ.'),
    ('10000000-0000-4000-8000-000000000019', 'Ускорение', 3, 'Преобразование', '1 действие', '30 футов', 'В, С, М', 'Концентрация, до 1 минуты', 'Ускоряет выбранное существо.'),
    ('10000000-0000-4000-8000-000000000020', 'Изгнание', 4, 'Ограждение', '1 действие', '60 футов', 'В, С, М', 'Концентрация, до 1 минуты', 'Изгоняет цель на другой план или в безопасное измерение.'),
    ('10000000-0000-4000-8000-000000000021', 'Большая невидимость', 4, 'Иллюзия', '1 действие', 'Касание', 'В, С', 'Концентрация, до 1 минуты', 'Делает существо невидимым даже при атаке.'),
    ('10000000-0000-4000-8000-000000000022', 'Ледяная буря', 4, 'Воплощение', '1 действие', '300 футов', 'В, С, М', 'Мгновенная', 'Град и лёд наносят урон в области.'),
    ('10000000-0000-4000-8000-000000000023', 'Массовое лечение ран', 5, 'Воплощение', '1 действие', '60 футов', 'В, С', 'Мгновенная', 'Восстанавливает хиты нескольким существам.'),
    ('10000000-0000-4000-8000-000000000024', 'Конус холода', 5, 'Воплощение', '1 действие', 'На себя', 'В, С, М', 'Мгновенная', 'Конус холода наносит сильный урон.'),
    ('10000000-0000-4000-8000-000000000025', 'Удержание чудовища', 5, 'Очарование', '1 действие', '90 футов', 'В, С, М', 'Концентрация, до 1 минуты', 'Парализует существо при провале спасброска.')
ON CONFLICT (spell_id) DO UPDATE SET
    spell_name = EXCLUDED.spell_name,
    spell_level = EXCLUDED.spell_level,
    spell_school = EXCLUDED.spell_school,
    casting_time = EXCLUDED.casting_time,
    spell_range = EXCLUDED.spell_range,
    components = EXCLUDED.components,
    duration = EXCLUDED.duration,
    spell_description = EXCLUDED.spell_description;

INSERT INTO dnd_editor.character_saving_throws
(
    character_saving_throw_id,
    character_id,
    ability_id,
    proficiency_level
)
SELECT
    gen_random_uuid(),
    ch.character_id,
    a.ability_id,
    CASE
        WHEN c.class_name = 'Варвар'     AND a.code IN ('STRENGTH', 'CONSTITUTION') THEN 1
        WHEN c.class_name = 'Бард'       AND a.code IN ('DEXTERITY', 'CHARISMA') THEN 1
        WHEN c.class_name = 'Жрец'       AND a.code IN ('WISDOM', 'CHARISMA') THEN 1
        WHEN c.class_name = 'Друид'      AND a.code IN ('INTELLIGENCE', 'WISDOM') THEN 1
        WHEN c.class_name = 'Воин'       AND a.code IN ('STRENGTH', 'CONSTITUTION') THEN 1
        WHEN c.class_name = 'Монах'      AND a.code IN ('STRENGTH', 'DEXTERITY') THEN 1
        WHEN c.class_name = 'Паладин'    AND a.code IN ('WISDOM', 'CHARISMA') THEN 1
        WHEN c.class_name = 'Следопыт'   AND a.code IN ('STRENGTH', 'DEXTERITY') THEN 1
        WHEN c.class_name = 'Плут'       AND a.code IN ('DEXTERITY', 'INTELLIGENCE') THEN 1
        WHEN c.class_name = 'Чародей'    AND a.code IN ('CONSTITUTION', 'CHARISMA') THEN 1
        WHEN c.class_name = 'Колдун'     AND a.code IN ('WISDOM', 'CHARISMA') THEN 1
        WHEN c.class_name = 'Волшебник'  AND a.code IN ('INTELLIGENCE', 'WISDOM') THEN 1
        ELSE 0
        END AS proficiency_level
FROM dnd_editor.characters ch
         JOIN dnd_editor.classes c ON c.class_id = ch.class_id
         CROSS JOIN dnd_editor.abilities a
ON CONFLICT (character_id, ability_id) DO UPDATE SET
    proficiency_level = EXCLUDED.proficiency_level;

WITH class_spell_map
         (
          class_name,
          spell_name,
          is_prepared
             )
         AS (
        VALUES
            ('Бард', 'Малая иллюзия', false),
            ('Бард', 'Свет', false),
            ('Бард', 'Громовая волна', false),
            ('Бард', 'Обнаружение магии', false),
            ('Бард', 'Невидимость', false),
            ('Бард', 'Удержание личности', false),
            ('Бард', 'Большая невидимость', false),
            ('Бард', 'Удержание чудовища', false),

            ('Жрец', 'Свет', true),
            ('Жрец', 'Лечение ран', true),
            ('Жрец', 'Благословение', true),
            ('Жрец', 'Обнаружение магии', true),
            ('Жрец', 'Духовное оружие', true),
            ('Жрец', 'Возрождение', true),
            ('Жрец', 'Изгнание', true),
            ('Жрец', 'Массовое лечение ран', true),

            ('Друид', 'Лечение ран', true),
            ('Друид', 'Обнаружение магии', true),
            ('Друид', 'Громовая волна', true),
            ('Друид', 'Паутина', true),
            ('Друид', 'Изгнание', true),
            ('Друид', 'Массовое лечение ран', true),

            ('Паладин', 'Лечение ран', true),
            ('Паладин', 'Благословение', true),
            ('Паладин', 'Обнаружение магии', true),
            ('Паладин', 'Возрождение', true),

            ('Следопыт', 'Лечение ран', false),
            ('Следопыт', 'Обнаружение магии', false),
            ('Следопыт', 'Паутина', false),

            ('Чародей', 'Огненный снаряд', false),
            ('Чародей', 'Луч холода', false),
            ('Чародей', 'Волшебная стрела', false),
            ('Чародей', 'Щит', false),
            ('Чародей', 'Громовая волна', false),
            ('Чародей', 'Туманный шаг', false),
            ('Чародей', 'Невидимость', false),
            ('Чародей', 'Огненный шар', false),
            ('Чародей', 'Контрзаклинание', false),
            ('Чародей', 'Молния', false),
            ('Чародей', 'Ускорение', false),
            ('Чародей', 'Большая невидимость', false),
            ('Чародей', 'Конус холода', false),

            ('Колдун', 'Малая иллюзия', false),
            ('Колдун', 'Невидимость', false),
            ('Колдун', 'Удержание личности', false),
            ('Колдун', 'Изгнание', false),
            ('Колдун', 'Удержание чудовища', false),

            ('Волшебник', 'Огненный снаряд', false),
            ('Волшебник', 'Свет', false),
            ('Волшебник', 'Малая иллюзия', false),
            ('Волшебник', 'Луч холода', false),
            ('Волшебник', 'Волшебная стрела', false),
            ('Волшебник', 'Щит', false),
            ('Волшебник', 'Обнаружение магии', false),
            ('Волшебник', 'Громовая волна', false),
            ('Волшебник', 'Туманный шаг', false),
            ('Волшебник', 'Невидимость', false),
            ('Волшебник', 'Удержание личности', false),
            ('Волшебник', 'Паутина', false),
            ('Волшебник', 'Огненный шар', false),
            ('Волшебник', 'Контрзаклинание', false),
            ('Волшебник', 'Молния', false),
            ('Волшебник', 'Ускорение', false),
            ('Волшебник', 'Изгнание', false),
            ('Волшебник', 'Большая невидимость', false),
            ('Волшебник', 'Ледяная буря', false),
            ('Волшебник', 'Конус холода', false),
            ('Волшебник', 'Удержание чудовища', false)
    ),
     character_spell_candidates AS (
         SELECT
             ch.character_id,
             sp.spell_id,
             csm.is_prepared
         FROM dnd_editor.characters ch
                  JOIN dnd_editor.classes c
                       ON c.class_id = ch.class_id
                  JOIN class_spell_map csm
                       ON csm.class_name = c.class_name
                  JOIN dnd_editor.spells sp
                       ON sp.spell_name = csm.spell_name
         WHERE c.is_spellcaster = true
           AND ch.level >= c.spellcasting_start_level
           AND sp.spell_level <= CASE
                                     WHEN c.class_name IN ('Паладин', 'Следопыт') THEN
                                         CASE
                                             WHEN ch.level >= 17 THEN 5
                                             WHEN ch.level >= 13 THEN 4
                                             WHEN ch.level >= 9  THEN 3
                                             WHEN ch.level >= 5  THEN 2
                                             WHEN ch.level >= 2  THEN 1
                                             ELSE 0
                                             END
                                     ELSE
                                         CASE
                                             WHEN ch.level >= 9 THEN 5
                                             WHEN ch.level >= 7 THEN 4
                                             WHEN ch.level >= 5 THEN 3
                                             WHEN ch.level >= 3 THEN 2
                                             WHEN ch.level >= 1 THEN 1
                                             ELSE 0
                                             END
             END
     )
INSERT INTO dnd_editor.character_spells
(
    character_spell_id,
    character_id,
    spell_id,
    is_prepared
)
SELECT
    gen_random_uuid(),
    csc.character_id,
    csc.spell_id,
    csc.is_prepared
FROM character_spell_candidates csc
ON CONFLICT (character_id, spell_id) DO UPDATE SET
    is_prepared = EXCLUDED.is_prepared;

UPDATE dnd_editor.characters ch
SET spells = spell_ids.spells
FROM (
         SELECT character_id, json_agg(spell_id)::text AS spells
         FROM dnd_editor.character_spells
         GROUP BY character_id
     ) spell_ids
WHERE ch.character_id = spell_ids.character_id
  AND ch.spells IS NULL;

UPDATE dnd_editor.characters ch
SET saving_throws_count = saving_throw_counts.saving_throws_count
FROM (
         SELECT character_id, COUNT(*)::int AS saving_throws_count
         FROM dnd_editor.character_saving_throws
         WHERE proficiency_level > 0
         GROUP BY character_id
     ) saving_throw_counts
WHERE ch.character_id = saving_throw_counts.character_id
  AND ch.saving_throws_count = 0;
