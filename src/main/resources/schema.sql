DROP TABLE IF EXISTS material_stock_history;
DROP TABLE IF EXISTS purchase_order;
DROP TABLE IF EXISTS production;
DROP TABLE IF EXISTS recipe;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS product;

------product------
CREATE TABLE product (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    CONSTRAINT uk_product_name UNIQUE (product_name)
);
-------------------

------material------
CREATE TABLE material (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    material_name VARCHAR(100) NOT NULL,
    stock_quantity INT NOT NULL,
    safety_stock_quantity INT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    CONSTRAINT uk_material_name UNIQUE (material_name)
);
--------------------

------recipe------
CREATE TABLE recipe (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    product_id BIGINT NOT NULL,
    material_id BIGINT NOT NULL,
    required_quantity INT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,

    -- 外部キー
    CONSTRAINT fk_recipe_product
        FOREIGN KEY (product_id)
        REFERENCES product (id),

    CONSTRAINT fk_recipe_material
        FOREIGN KEY (material_id)
        REFERENCES material (id),

    -- 論理的な重複防止
    CONSTRAINT uk_recipe_product_material
        UNIQUE (product_id, material_id)
);
------------------

------production------
CREATE TABLE production (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    production_date DATE NOT NULL,
    quantity INT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,

    CONSTRAINT fk_production_product
        FOREIGN KEY (product_id)
        REFERENCES product (id)
);
----------------------

------material_stock_history-------
CREATE TABLE material_stock_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    material_id BIGINT NOT NULL,
    change_date DATETIME NOT NULL,
    change_type VARCHAR(20) NOT NULL,
    stock_quantity INT NOT NULL,
    CONSTRAINT fk_material
        FOREIGN KEY (material_id)
        REFERENCES material(id)
);
-----------------------------------

