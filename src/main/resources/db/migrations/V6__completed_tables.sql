BEGIN;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- =========================
-- races
-- =========================
INSERT INTO dnd_editor.races
(
    race_id,
    name,
    description,
    age,
    height,
    speed,
    languages
)
VALUES
    (
        '7f2b6f3e-7b9a-4d2c-9c5b-1f8a0a6e4c11',
        'Человек',
        'Люди универсальны, амбициозны и легко приспосабливаются к разным условиям и культурам.',
        80,
        170,
        30,
        'Общий'
    ),
    (
        'c4a3f2d7-6c5e-4e2f-a7c1-92c0d3b8f214',
        'Эльф',
        'Эльфы — долгоживущая раса с природной грацией, острым восприятием и склонностью к магии.',
        750,
        165,
        30,
        'Общий, Эльфийский'
    ),
    (
        '2e91b24c-3f88-4f8e-b4b7-4c93e9c2a6d3',
        'Дварф',
        'Дварфы известны стойкостью, мастерством ремёсел, крепким телосложением и любовью к камню.',
        350,
        140,
        25,
        'Общий, Дварфийский'
    ),
    (
        '9a6d15b8-5c3f-4f41-8d72-7b3f1c9e0a55',
        'Полурослик',
        'Полурослики отличаются ловкостью, удачливостью, дружелюбием и умением избегать опасности.',
        150,
        90,
        25,
        'Общий, Полуросликов'
    ),
    (
        'f1c8e3a2-9b47-4a6e-9b2c-5e6f7a8d1c90',
        'Драконорождённый',
        'Драконорождённые несут драконье наследие, отличаются силой, гордостью и врождённой энергией.',
        80,
        190,
        30,
        'Общий, Драконий'
    ),
    (
        '3d5b9c71-8a2e-4f6b-a1d9-6e7c2b4f8a13',
        'Гном',
        'Гномы — любознательная и изобретательная раса, склонная к магии, ремеслу и исследованиям.',
        500,
        100,
        25,
        'Общий, Гномий'
    ),
    (
        'a8f2c6d4-1e9b-4c73-8f5a-2d6e0b9c4a77',
        'Полуэльф',
        'Полуэльфы сочетают человеческую гибкость и эльфийское наследие, часто проявляя харизму.',
        180,
        170,
        30,
        'Общий, Эльфийский'
    ),
    (
        '5b7e2c9f-4a1d-4f63-b8c2-9e3f6a0d1b84',
        'Полуорк',
        'Полуорки отличаются физической силой, стойкостью и решительностью.',
        75,
        180,
        30,
        'Общий, Орочий'
    ),
    (
        'e6a3d8b1-2c7f-4a90-9b6e-1f4c8d2a7e35',
        'Тифлинг',
        'Тифлинги обладают инфернальным наследием, необычной внешностью и врождённой магической силой.',
        100,
        170,
        30,
        'Общий, Инфернальный'
    )
ON CONFLICT (name) DO UPDATE SET
                                 description = EXCLUDED.description,
                                 age = EXCLUDED.age,
                                 height = EXCLUDED.height,
                                 speed = EXCLUDED.speed,
                                 languages = EXCLUDED.languages;

-- =========================
-- subraces
-- =========================
WITH subrace_data
         (
          subrace_id,
          race_name,
          name,
          description
             )
         AS (
        VALUES
            ('4b7c1a2e-8f3d-4c9a-9e5b-1d6f2a8c7b40', 'Эльф', 'Высший эльф', 'Высшие эльфы отличаются развитым интеллектом, магической подготовкой и древними знаниями.'),
            ('5c8d2b3f-9a4e-4d1b-8f6c-2e7a3b9d8c51', 'Эльф', 'Лесной эльф', 'Лесные эльфы связаны с природой, быстры, скрытны и хорошо ориентируются в лесах.'),
            ('6d9e3c4a-1b5f-4e2c-9a7d-3f8b4c1e9d62', 'Эльф', 'Тёмный эльф', 'Тёмные эльфы, или дроу, приспособлены к подземной жизни и обладают врождённой магией.'),

            ('7e1f4d5b-2c6a-4f3d-8b1e-4a9c5d2f1e73', 'Дварф', 'Горный дварф', 'Горные дварфы сильны, воинственны и привыкли к суровым горным условиям.'),
            ('8f2a5e6c-3d7b-4a4e-9c2f-5b1d6e3a2f84', 'Дварф', 'Холмовой дварф', 'Холмовые дварфы отличаются крепким здоровьем, выносливостью и устойчивостью.'),

            ('9a3b6f7d-4e8c-4b5f-8d3a-6c2e7f4b3a95', 'Полурослик', 'Легконогий полурослик', 'Легконогие полурослики общительны, ловки и умеют оставаться незаметными.'),
            ('1b4c7a8e-5f9d-4c6a-9e4b-7d3f8a5c4b16', 'Полурослик', 'Коренастый полурослик', 'Коренастые полурослики более выносливы и устойчивы к опасным воздействиям.'),

            ('2c5d8b9f-6a1e-4d7b-8f5c-8e4a9b6d5c27', 'Гном', 'Лесной гном', 'Лесные гномы скрытны, любознательны и связаны с природной иллюзорной магией.'),
            ('3d6e9c1a-7b2f-4e8c-9a6d-9f5b1c7e6d38', 'Гном', 'Скальный гном', 'Скальные гномы склонны к изобретательству, ремеслу и изучению механизмов.')
    )
INSERT INTO dnd_editor.subraces
(
    subrace_id,
    race_id,
    name,
    description
)
SELECT
    sd.subrace_id::uuid,
    r.race_id,
    sd.name,
    sd.description
FROM subrace_data sd
         JOIN dnd_editor.races r ON r.name = sd.race_name
ON CONFLICT (race_id, name) DO UPDATE SET
    description = EXCLUDED.description;

-- =========================
-- classes
-- =========================
INSERT INTO dnd_editor.classes
(
    class_id,
    class_name,
    class_description,
    is_spellcaster,
    spellcasting_start_level
)
VALUES
    ('0a4d7b2f-6c1e-4f83-9a5d-2b7e6c1f8a40', 'Варвар', 'Воин ярости, силы и выносливости, полагающийся на ближний бой.', false, NULL),
    ('1b5e8c3a-7d2f-4a94-8b6e-3c8f7d2a9b51', 'Бард', 'Универсальный заклинатель, использующий музыку, вдохновение и социальные навыки.', true, 1),
    ('2c6f9d4b-8e3a-4b85-9c7f-4d9a8e3b1c62', 'Жрец', 'Божественный заклинатель, способный лечить, защищать и поддерживать союзников.', true, 1),
    ('3d7a1e5c-9f4b-4c96-8d1a-5e1b9f4c2d73', 'Друид', 'Заклинатель природы, связанный со стихиями, животными и природной магией.', true, 1),
    ('4e8b2f6d-1a5c-4d87-9e2b-6f2c1a5d3e84', 'Воин', 'Мастер оружия и боевых техник, гибкий в выборе стиля сражения.', false, NULL),
    ('5f9c3a7e-2b6d-4e98-8f3c-7a3d2b6e4f95', 'Монах', 'Подвижный боец, использующий дисциплину, скорость и внутреннюю энергию.', false, NULL),
    ('6a1d4b8f-3c7e-4f89-9a4d-8b4e3c7f5a16', 'Паладин', 'Священный воин, сочетающий боевую подготовку, клятву и божественную магию.', true, 2),
    ('7b2e5c9a-4d8f-4a90-8b5e-9c5f4d8a6b27', 'Следопыт', 'Охотник и исследователь, связанный с природой, выживанием и выслеживанием.', true, 2),
    ('8c3f6d1b-5e9a-4b81-9c6f-1d6a5e9b7c38', 'Плут', 'Ловкий и скрытный персонаж, мастер точных атак, хитрости и проникновения.', false, NULL),
    ('9d4a7e2c-6f1b-4c92-8d7a-2e7b6f1c8d49', 'Чародей', 'Заклинатель с врождённой магической силой и гибким использованием заклинаний.', true, 1),
    ('1e5b8f3d-7a2c-4d83-9e8b-3f8c7a2d9e50', 'Колдун', 'Заклинатель, получивший силу через договор с могущественным покровителем.', true, 1),
    ('2f6c9a4e-8b3d-4e94-8f9c-4a9d8b3e1f61', 'Волшебник', 'Учёный маг, изучающий заклинания через книги, знания и подготовку.', true, 1)
ON CONFLICT (class_name) DO UPDATE SET
                                       class_description = EXCLUDED.class_description,
                                       is_spellcaster = EXCLUDED.is_spellcaster,
                                       spellcasting_start_level = EXCLUDED.spellcasting_start_level;

-- =========================
-- class_archetypes
-- =========================
WITH archetype_data
         (
          class_archetype_id,
          class_name,
          name,
          description
             )
         AS (
        VALUES
            ('0f1a2b3c-4d5e-4f60-8a91-1b2c3d4e5f60', 'Варвар', 'Путь берсерка', 'Архетип неудержимой ярости, агрессивного боя и высокого урона.'),
            ('1a2b3c4d-5e6f-4a71-9b82-2c3d4e5f6a71', 'Варвар', 'Путь тотемного воина', 'Архетип, связанный с духами природы и тотемными силами.'),

            ('2b3c4d5e-6f7a-4b82-8c93-3d4e5f6a7b82', 'Бард', 'Коллегия знаний', 'Архетип широких знаний, навыков, поддержки и социальной гибкости.'),
            ('3c4d5e6f-7a8b-4c93-9d14-4e5f6a7b8c93', 'Бард', 'Коллегия доблести', 'Архетип, сочетающий магическую поддержку и боевую подготовку.'),

            ('4d5e6f7a-8b9c-4d14-8e25-5f6a7b8c9d14', 'Жрец', 'Домен жизни', 'Домен исцеления, защиты и поддержания жизненных сил союзников.'),
            ('5e6f7a8b-9c1d-4e25-9f36-6a7b8c9d1e25', 'Жрец', 'Домен света', 'Домен света, защиты от тьмы и атакующей божественной магии.'),

            ('6f7a8b9c-1d2e-4f36-8a47-7b8c9d1e2f36', 'Друид', 'Круг земли', 'Круг, связанный с местностью и усиленной природной магией.'),
            ('7a8b9c1d-2e3f-4a47-9b58-8c9d1e2f3a47', 'Друид', 'Круг луны', 'Круг, ориентированный на активное использование дикого облика.'),

            ('8b9c1d2e-3f4a-4b58-8c69-9d1e2f3a4b58', 'Воин', 'Чемпион', 'Архетип физического совершенства, простого и надёжного боя.'),
            ('9c1d2e3f-4a5b-4c69-9d70-1e2f3a4b5c69', 'Воин', 'Мастер боевых приёмов', 'Архетип тактики, манёвров и специальных боевых техник.'),

            ('1d2e3f4a-5b6c-4d70-8e81-2f3a4b5c6d70', 'Монах', 'Путь открытой ладони', 'Архетип контроля противника и совершенствования рукопашного боя.'),
            ('2e3f4a5b-6c7d-4e81-9f92-3a4b5c6d7e81', 'Монах', 'Путь тени', 'Архетип скрытности, мобильности и действий из тени.'),

            ('3f4a5b6c-7d8e-4f92-8a13-4b5c6d7e8f92', 'Паладин', 'Клятва преданности', 'Клятва справедливости, защиты и верности идеалам.'),
            ('4a5b6c7d-8e9f-4a13-9b24-5c6d7e8f9a13', 'Паладин', 'Клятва мести', 'Клятва преследования опасных врагов и решительного возмездия.'),

            ('5b6c7d8e-9f1a-4b24-8c35-6d7e8f9a1b24', 'Следопыт', 'Охотник', 'Архетип выслеживания, тактического боя и уничтожения противников.'),
            ('6c7d8e9f-1a2b-4c35-9d46-7e8f9a1b2c35', 'Следопыт', 'Повелитель зверей', 'Архетип взаимодействия с животным спутником.'),

            ('7d8e9f1a-2b3c-4d46-8e57-8f9a1b2c3d46', 'Плут', 'Вор', 'Архетип скрытности, проникновения, ловкости и работы с предметами.'),
            ('8e9f1a2b-3c4d-4e57-9f68-9a1b2c3d4e57', 'Плут', 'Убийца', 'Архетип внезапных атак и устранения целей.'),

            ('9f1a2b3c-4d5e-4f68-8a79-1b2c3d4e5f68', 'Чародей', 'Драконья кровь', 'Происхождение, связанное с драконьей силой и врождённой магией.'),
            ('1a2b3c4d-5e6f-4a79-9b80-2c3d4e5f6a79', 'Чародей', 'Дикая магия', 'Происхождение нестабильной и непредсказуемой магической силы.'),

            ('2b3c4d5e-6f7a-4b80-8c91-3d4e5f6a7b80', 'Колдун', 'Покровитель Архифея', 'Покровитель, связанный с фейской магией, обманом и чарами.'),
            ('3c4d5e6f-7a8b-4c91-9d12-4e5f6a7b8c91', 'Колдун', 'Покровитель Исчадие', 'Покровитель, связанный с разрушительной потусторонней силой.'),

            ('4d5e6f7a-8b9c-4d12-8e23-5f6a7b8c9d12', 'Волшебник', 'Школа воплощения', 'Школа магии, создающей мощные энергетические эффекты и урон.'),
            ('5e6f7a8b-9c1d-4e23-9f34-6a7b8c9d1e23', 'Волшебник', 'Школа иллюзии', 'Школа иллюзорных образов, обмана восприятия и отвлечения.')
    )
INSERT INTO dnd_editor.class_archetypes
(
    class_archetype_id,
    class_id,
    name,
    description
)
SELECT
    ad.class_archetype_id::uuid,
    c.class_id,
    ad.name,
    ad.description
FROM archetype_data ad
         JOIN dnd_editor.classes c ON c.class_name = ad.class_name
ON CONFLICT (class_id, name) DO UPDATE SET
    description = EXCLUDED.description;

-- =========================
-- skills
-- Важно: в текущей схеме skills.ability VARCHAR(10),
-- поэтому используются короткие коды STR/DEX/INT/WIS/CHA.
-- =========================
INSERT INTO dnd_editor.skills
(
    skill_id,
    name,
    ability
)
VALUES
    ('0f3a7c2d-1e5b-4a90-8c6d-2f7b1a9e3c40', 'Атлетика', 'STR'),

    ('1a4b8d3e-2f6c-4b91-9d7e-3a8c2b1f4d51', 'Акробатика', 'DEX'),
    ('2b5c9e4f-3a7d-4c82-8e1f-4b9d3c2a5e62', 'Ловкость рук', 'DEX'),
    ('3c6d1f5a-4b8e-4d93-9f2a-5c1e4d3b6f73', 'Скрытность', 'DEX'),

    ('4d7e2a6b-5c9f-4e84-8a3b-6d2f5e4c7a84', 'Магия', 'INT'),
    ('5e8f3b7c-6d1a-4f95-9b4c-7e3a6f5d8b95', 'История', 'INT'),
    ('6f9a4c8d-7e2b-4a86-8c5d-8f4b7a6e9c16', 'Расследование', 'INT'),
    ('7a1b5d9e-8f3c-4b97-9d6e-9a5c8b7f1d27', 'Природа', 'INT'),
    ('8b2c6e1f-9a4d-4c88-8e7f-1b6d9c8a2e38', 'Религия', 'INT'),

    ('9c3d7f2a-1b5e-4d99-9f8a-2c7e1d9b3f49', 'Уход за животными', 'WIS'),
    ('1d4e8a3b-2c6f-4e80-8a9b-3d8f2e1c4a50', 'Проницательность', 'WIS'),
    ('2e5f9b4c-3d7a-4f91-9b1c-4e9a3f2d5b61', 'Медицина', 'WIS'),
    ('3f6a1c5d-4e8b-4a82-8c2d-5f1b4a3e6c72', 'Внимательность', 'WIS'),
    ('4a7b2d6e-5f9c-4b93-9d3e-6a2c5b4f7d83', 'Выживание', 'WIS'),

    ('5b8c3e7f-6a1d-4c84-8e4f-7b3d6c5a8e94', 'Обман', 'CHA'),
    ('6c9d4f1a-7b2e-4d95-9f5a-8c4e7d6b9f15', 'Запугивание', 'CHA'),
    ('7d1e5a2b-8c3f-4e86-8a6b-9d5f8e7c1a26', 'Выступление', 'CHA'),
    ('8e2f6b3c-9d4a-4f97-9b7c-1e6a9f8d2b37', 'Убеждение', 'CHA')
ON CONFLICT (name) DO UPDATE SET
    ability = EXCLUDED.ability;

-- =========================
-- character_saving_throws
-- Заполняет спасброски для всех существующих characters.
-- proficiency_level:
-- 0 = нет владения
-- 1 = владение
-- =========================
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

-- =========================
-- character_skills
-- Заполняет навыки для всех существующих characters.
-- proficiency_level:
-- 0 = нет владения
-- 1 = владение
-- 2 = экспертность / усиленное владение
-- =========================
WITH class_skill_proficiencies
         (
          class_name,
          skill_name,
          proficiency_level
             )
         AS (
        VALUES
            ('Варвар', 'Атлетика', 1),
            ('Варвар', 'Запугивание', 1),
            ('Варвар', 'Выживание', 1),

            ('Бард', 'Выступление', 1),
            ('Бард', 'Убеждение', 1),
            ('Бард', 'Обман', 1),
            ('Бард', 'История', 1),
            ('Бард', 'Магия', 1),

            ('Жрец', 'Религия', 1),
            ('Жрец', 'Медицина', 1),
            ('Жрец', 'Проницательность', 1),

            ('Друид', 'Природа', 1),
            ('Друид', 'Уход за животными', 1),
            ('Друид', 'Выживание', 1),
            ('Друид', 'Медицина', 1),

            ('Воин', 'Атлетика', 1),
            ('Воин', 'Запугивание', 1),
            ('Воин', 'Внимательность', 1),

            ('Монах', 'Акробатика', 1),
            ('Монах', 'Атлетика', 1),
            ('Монах', 'Проницательность', 1),

            ('Паладин', 'Атлетика', 1),
            ('Паладин', 'Религия', 1),
            ('Паладин', 'Убеждение', 1),

            ('Следопыт', 'Выживание', 1),
            ('Следопыт', 'Внимательность', 1),
            ('Следопыт', 'Природа', 1),
            ('Следопыт', 'Уход за животными', 1),

            ('Плут', 'Скрытность', 2),
            ('Плут', 'Ловкость рук', 2),
            ('Плут', 'Акробатика', 1),
            ('Плут', 'Обман', 1),
            ('Плут', 'Расследование', 1),

            ('Чародей', 'Магия', 1),
            ('Чародей', 'Обман', 1),
            ('Чародей', 'Убеждение', 1),

            ('Колдун', 'Магия', 1),
            ('Колдун', 'Запугивание', 1),
            ('Колдун', 'Обман', 1),

            ('Волшебник', 'Магия', 1),
            ('Волшебник', 'История', 1),
            ('Волшебник', 'Расследование', 1)
    )
INSERT INTO dnd_editor.character_skills
(
    character_skill_id,
    character_id,
    skill_id,
    proficiency_level
)
SELECT
    gen_random_uuid(),
    ch.character_id,
    s.skill_id,
    COALESCE(csp.proficiency_level, 0) AS proficiency_level
FROM dnd_editor.characters ch
         JOIN dnd_editor.classes c ON c.class_id = ch.class_id
         CROSS JOIN dnd_editor.skills s
         LEFT JOIN class_skill_proficiencies csp
                   ON csp.class_name = c.class_name
                       AND csp.skill_name = s.name
ON CONFLICT (character_id, skill_id) DO UPDATE SET
    proficiency_level = EXCLUDED.proficiency_level;

-- =========================
-- character_spells
-- Заполняет заклинания для существующих персонажей-заклинателей.
-- Работает только если dnd_editor.spells уже заполнена.
-- is_prepared:
-- true для подготовленных заклинателей,
-- false для известных/спонтанных заклинателей.
-- =========================
WITH class_spell_map
         (
          class_name,
          spell_name,
          is_prepared
             )
         AS (
        VALUES
            -- Бард
            ('Бард', 'Малая иллюзия', false),
            ('Бард', 'Свет', false),
            ('Бард', 'Громовая волна', false),
            ('Бард', 'Обнаружение магии', false),
            ('Бард', 'Невидимость', false),
            ('Бард', 'Удержание личности', false),
            ('Бард', 'Большая невидимость', false),
            ('Бард', 'Удержание чудовища', false),

            -- Жрец
            ('Жрец', 'Свет', true),
            ('Жрец', 'Лечение ран', true),
            ('Жрец', 'Благословение', true),
            ('Жрец', 'Обнаружение магии', true),
            ('Жрец', 'Духовное оружие', true),
            ('Жрец', 'Возрождение', true),
            ('Жрец', 'Изгнание', true),
            ('Жрец', 'Массовое лечение ран', true),

            -- Друид
            ('Друид', 'Лечение ран', true),
            ('Друид', 'Обнаружение магии', true),
            ('Друид', 'Громовая волна', true),
            ('Друид', 'Паутина', true),
            ('Друид', 'Изгнание', true),
            ('Друид', 'Массовое лечение ран', true),

            -- Паладин
            ('Паладин', 'Лечение ран', true),
            ('Паладин', 'Благословение', true),
            ('Паладин', 'Обнаружение магии', true),
            ('Паладин', 'Возрождение', true),

            -- Следопыт
            ('Следопыт', 'Лечение ран', false),
            ('Следопыт', 'Обнаружение магии', false),
            ('Следопыт', 'Паутина', false),

            -- Чародей
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

            -- Колдун
            ('Колдун', 'Малая иллюзия', false),
            ('Колдун', 'Невидимость', false),
            ('Колдун', 'Удержание личности', false),
            ('Колдун', 'Изгнание', false),
            ('Колдун', 'Удержание чудовища', false),

            -- Волшебник
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

COMMIT;