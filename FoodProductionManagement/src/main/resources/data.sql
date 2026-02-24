------product------
INSERT IGNORE INTO product (id, product_name, created_at, updated_at)
VALUES
(1, 'わかめ', NOW(), NOW()),    
(2, '鮭', NOW(), NOW()),   
(3, '若菜', NOW(), NOW()), 
(4, '梅じそ', NOW(), NOW()),
(5, 'しらす', NOW(), NOW()),
(6, 'ツナマヨ', NOW(), NOW());
-------------------

------material------
INSERT IGNORE INTO material (id, material_name, stock_quantity, safety_stock_quantity, created_at, updated_at)
VALUES
(1, 'material_a', 250, 125, NOW(), NOW()), 
(2, 'material_b', 100, 50,  NOW(), NOW()), 
(3, 'material_c', 100, 50, NOW(), NOW()), 
(4, 'material_d', 100, 50, NOW(), NOW()), 
(5, 'material_e', 100, 50, NOW(), NOW()), 
(6, 'material_f', 100, 50, NOW(), NOW()), 
(7, 'material_g', 100, 50, NOW(), NOW()); 
--------------------

------recipe------
-- わかめ
INSERT IGNORE INTO recipe (id, product_id, material_id, required_quantity, created_at,
  updated_at)
VALUES
(1, 1, 1, 5, NOW(), NOW());

-- 鮭
INSERT IGNORE INTO recipe (id, product_id, material_id, required_quantity, created_at,
  updated_at)
VALUES
(2, 2, 1, 2, NOW(), NOW()),
(3, 2, 3, 2, NOW(), NOW()),
(4, 2, 2, 1, NOW(), NOW());

-- 若菜
INSERT IGNORE INTO recipe (id, product_id, material_id, required_quantity, created_at,
  updated_at)
VALUES
(5, 3, 1, 2, NOW(), NOW()),
(6, 3, 4, 2, NOW(), NOW()),
(7, 3, 2, 1, NOW(), NOW());

-- 梅じそ
INSERT IGNORE INTO recipe (id, product_id, material_id, required_quantity, created_at,
  updated_at)
VALUES
(8, 4, 1, 2, NOW(), NOW()),
(9, 4, 5, 2, NOW(), NOW()),
(10, 4, 2, 1, NOW(), NOW());

-- しらす
INSERT IGNORE INTO recipe (id, product_id, material_id, required_quantity, created_at,
  updated_at)
VALUES
(11, 5, 1, 2, NOW(), NOW()),
(12, 5, 6, 2, NOW(), NOW()),
(13, 5, 2, 1, NOW(), NOW());

-- ツナマヨ
INSERT IGNORE INTO recipe (id, product_id, material_id, required_quantity, created_at,
  updated_at)
VALUES
(14, 6, 1, 2, NOW(), NOW()),
(15, 6, 7, 2, NOW(), NOW()),
(16, 6, 2, 1, NOW(), NOW());
----------------

------material_stock_history-------
INSERT INTO material_stock_history
(material_id, change_date, change_type, stock_quantity)
VALUES
(1, NOW(), 'INITIAL', 250),
(2, NOW(), 'INITIAL', 100),
(3, NOW(), 'INITIAL', 100),
(4, NOW(), 'INITIAL', 100),
(5, NOW(), 'INITIAL', 100),
(6, NOW(), 'INITIAL', 100),
(7, NOW(), 'INITIAL', 100);
-----------------------------------

