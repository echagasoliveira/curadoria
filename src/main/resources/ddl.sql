-- Banco em MySQL:
drop table tb_hotel_internacional_palavra_chave;
drop table tb_hotel_internacional_regime;
drop table tb_hotel_internacional;
drop table tb_hotel_nacional_palavra_chave;
drop table tb_hotel_nacional_regime;
drop table tb_hotel_nacional;
drop table tb_municipio;
drop table tb_estado;
drop table tb_pais;
drop table tb_palavra_chave;
drop table tb_regime_alimentacao;
drop table tb_user_role;
drop table tb_role;
drop table tb_user;

-- Usuários
CREATE TABLE tb_user (
    id char(37) PRIMARY KEY,
    apple_user_id INT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
	data_expiracao_assinatura DATE NULL
);

--alter table tb_user add data_expiracao_assinatura date NULL;

-- Papéis (roles)
CREATE TABLE tb_role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    authority VARCHAR(50) NOT NULL UNIQUE
);

-- Relação entre usuários e papéis
CREATE TABLE tb_user_role (
    user_id char(37) NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES tb_user(id),
    FOREIGN KEY (role_id) REFERENCES tb_role(id)
);

-- Regimes de alimentação
CREATE TABLE tb_regime_alimentacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(500) NOT NULL unique
);

-- Palavras-chave
CREATE TABLE tb_palavra_chave (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL unique
);


-- Continente
CREATE TABLE tb_continente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

-- Países
CREATE TABLE tb_pais (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_continente int NOT NULL,
	sigla2 CHAR(2) NOT NULL,
    sigla3 CHAR(3) NOT NULL,
    nome VARCHAR(100) NOT NULL,
	FOREIGN KEY (id_continente) REFERENCES tb_continente(id)
);

-- Estados
CREATE TABLE tb_estado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    uf CHAR(2) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    latitude FLOAT,
    longitude FLOAT,
    regiao VARCHAR(50)
);

-- Municípios
CREATE TABLE tb_municipio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo_igbe INT NULL,
    id_pais INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    latitude FLOAT,
    longitude FLOAT,
    capital BOOLEAN NULL,
    id_estado INT NULL,
    id_siafi VARCHAR(10),
    ddd INT,
    fuso_horario VARCHAR(50),
    FOREIGN KEY (id_pais) REFERENCES tb_pais(id),
    FOREIGN KEY (id_estado) REFERENCES tb_estado(id)
);

-- Hotel Nacional
CREATE TABLE tb_hotel_nacional (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_municipio INT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    url varchar(255) NULL,
    ativo BOOLEAN DEFAULT TRUE,
	palavras_chave varchar(255) NULL,
    FOREIGN KEY (id_municipio) REFERENCES tb_municipio(id)
);

-- Hotel Nacional - Regimes
CREATE TABLE tb_hotel_nacional_regime (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_hotel_nacional INT NOT NULL,
    id_regime_alimentacao INT NOT NULL,
	FOREIGN KEY (id_hotel_nacional) REFERENCES tb_hotel_nacional(id),
    FOREIGN KEY (id_regime_alimentacao) REFERENCES tb_regime_alimentacao(id)
);

-- Hotel Nacional - Palavras-chave
/*CREATE TABLE tb_hotel_nacional_palavra_chave (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_hotel_nacional INT NOT NULL,
    id_palavra_chave INT NOT NULL,
    FOREIGN KEY (id_hotel_nacional) REFERENCES tb_hotel_nacional(id),
    FOREIGN KEY (id_palavra_chave) REFERENCES tb_palavra_chave(id)
);*/

-- Hotel Internacional
CREATE TABLE tb_hotel_internacional (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_municipio INT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    url varchar(255) NULL,
    ativo BOOLEAN DEFAULT TRUE,
	palavras_chave varchar(255) NULL,
    FOREIGN KEY (id_municipio) REFERENCES tb_municipio(id)
);

-- Hotel Internacional - Regimes
CREATE TABLE tb_hotel_internacional_regime (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_hotel_internacional INT NOT NULL,
    id_regime_alimentacao INT NOT NULL,
    FOREIGN KEY (id_hotel_internacional) REFERENCES tb_hotel_internacional(id),
    FOREIGN KEY (id_regime_alimentacao) REFERENCES tb_regime_alimentacao(id)
);

-- Hotel Internacional - Palavras-chave
/*CREATE TABLE tb_hotel_internacional_palavra_chave (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_hotel_internacional INT NOT NULL,
    id_palavra_chave INT NOT NULL,
    FOREIGN KEY (id_hotel_internacional) REFERENCES tb_hotel_internacional(id),
    FOREIGN KEY (id_palavra_chave) REFERENCES tb_palavra_chave(id)
);*/

-- Banco em SQL Server:
-- Usuários
CREATE TABLE tb_user (
    id char(37) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Papéis (roles)
CREATE TABLE tb_role (
    id INT IDENTITY(1,1) PRIMARY KEY,
    authority VARCHAR(50) NOT NULL UNIQUE
);

-- Relação entre usuários e papéis
CREATE TABLE tb_user_role (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES tb_user(id),
    FOREIGN KEY (role_id) REFERENCES tb_role(id)
);

-- Regimes de alimentação
CREATE TABLE tb_regime_alimentacao (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

-- Palavras-chave
CREATE TABLE tb_palavra_chave (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

-- Países
CREATE TABLE tb_pais (
    id INT IDENTITY(1,1) PRIMARY KEY,
    sigla_2 CHAR(2) NOT NULL,
    sigla_3 CHAR(3) NOT NULL,
    nome VARCHAR(100) NOT NULL
);

-- Estados
CREATE TABLE tb_estado (
    id INT IDENTITY(1,1) PRIMARY KEY,
    uf CHAR(2) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    latitude FLOAT,
    longitude FLOAT,
    regiao VARCHAR(50)
);

-- Municípios
CREATE TABLE tb_municipio (
    id INT IDENTITY(1,1) PRIMARY KEY,
    codi_igbe INT NOT NULL,
    id_pais INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    latitude FLOAT,
    longitude FLOAT,
    capital BIT NOT NULL,
    id_estado INT NOT NULL,
    id_siafi VARCHAR(10),
    ddd INT,
    fuso_horario VARCHAR(50),
    FOREIGN KEY (id_pais) REFERENCES tb_pais(id),
    FOREIGN KEY (id_estado) REFERENCES tb_estado(id)
);

-- Hotel Nacional
CREATE TABLE tb_hotel_nacional (
    id INT IDENTITY(1,1) PRIMARY KEY,
    id_municipio INT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_municipio) REFERENCES tb_municipio(id)
);

-- Hotel Nacional - Regimes
CREATE TABLE tb_hotel_nacional_regime (
    id INT IDENTITY(1,1) PRIMARY KEY,
    id_hotel_nacional INT NOT NULL,
    id_regime_alimentacao INT NOT NULL,
    FOREIGN KEY (id_hotel_nacional) REFERENCES tb_hotel_nacional(id),
    FOREIGN KEY (id_regime_alimentacao) REFERENCES tb_regime_alimentacao(id)
);

-- Hotel Nacional - Palavras-chave
/*CREATE TABLE tb_hotel_nacional_palavra_chave (
    id INT IDENTITY(1,1) PRIMARY KEY,
    id_hotel_nacional INT NOT NULL,
    id_palavra_chave INT NOT NULL,
    FOREIGN KEY (id_hotel_nacional) REFERENCES tb_hotel_nacional(id),
    FOREIGN KEY (id_palavra_chave) REFERENCES tb_palavra_chave(id)
);*/

-- Hotel Internacional
CREATE TABLE tb_hotel_internacional (
    id INT IDENTITY(1,1) PRIMARY KEY,
    id_municipio INT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_municipio) REFERENCES tb_municipio(id)
);

-- Hotel Internacional - Regimes
CREATE TABLE tb_hotel_internacional_regime (
    id INT IDENTITY(1,1) PRIMARY KEY,
    id_hotel_internacional INT NOT NULL,
    id_regime_alimentacao INT NOT NULL,
    FOREIGN KEY (id_hotel_internacional) REFERENCES tb_hotel_internacional(id),
    FOREIGN KEY (id_regime_alimentacao) REFERENCES tb_regime_alimentacao(id)
);

-- Hotel Nacional - Palavras-chave
/*CREATE TABLE tb_hotel_internacional_palavra_chave (
    id INT IDENTITY(1,1) PRIMARY KEY,
    id_hotel_internacional INT NOT NULL,
    id_palavra_chave INT NOT NULL,
    FOREIGN KEY (id_hotel_internacional) REFERENCES tb_hotel_internacional(id),
    FOREIGN KEY (id_palavra_chave) REFERENCES tb_palavra_chave(id)
);*/