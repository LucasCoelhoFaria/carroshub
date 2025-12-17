-- ===============================
-- EXTENSÕES
-- ===============================

-- UUID
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ===============================
-- BUSINESS (Empreendimentos)
-- ===============================

CREATE TABLE IF NOT EXISTS business (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    business_name VARCHAR(120) NOT NULL,
    google_token_hash TEXT NOT NULL,

    whatsapp VARCHAR(20),

    created_at TIMESTAMP DEFAULT now(),

    CONSTRAINT uq_business_name UNIQUE (business_name),
    CONSTRAINT uq_google_token UNIQUE (google_token_hash)
);

CREATE INDEX IF NOT EXISTS idx_business_name
    ON business (business_name);

CREATE INDEX IF NOT EXISTS idx_google_token_hash
    ON business (google_token_hash);

-- ===============================
-- SERVICES (Serviços da estética)
-- ===============================

CREATE TABLE IF NOT EXISTS service (
    id BIGSERIAL PRIMARY KEY,

    business_id UUID NOT NULL,

    name VARCHAR(120) NOT NULL,
    description TEXT,
    enabled BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT now(),

    CONSTRAINT fk_service_business
        FOREIGN KEY (business_id)
        REFERENCES business(id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_service_business
    ON service (business_id);

CREATE INDEX IF NOT EXISTS idx_service_enabled
    ON service (enabled);

-- ===============================
-- FAQ (Perguntas frequentes)
-- ===============================

CREATE TABLE IF NOT EXISTS faq (
    id BIGSERIAL PRIMARY KEY,

    business_id UUID NOT NULL,

    question TEXT NOT NULL,
    answer TEXT NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT now(),

    CONSTRAINT fk_faq_business
        FOREIGN KEY (business_id)
        REFERENCES business(id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_faq_business
    ON faq (business_id);

CREATE INDEX IF NOT EXISTS idx_faq_enabled
    ON faq (enabled);

-- ===============================
-- LANDING SECTIONS (Estrutura da landing)
-- ===============================

CREATE TABLE IF NOT EXISTS landing_section (
    id BIGSERIAL PRIMARY KEY,

    business_id UUID NOT NULL,

    -- HERO, SERVICES, FAQ, FOOTER
    type VARCHAR(50) NOT NULL,

    enabled BOOLEAN DEFAULT TRUE,

    -- Conteúdo flexível (JSON)
    content JSONB,

    created_at TIMESTAMP DEFAULT now(),

    CONSTRAINT fk_landing_section_business
        FOREIGN KEY (business_id)
        REFERENCES business(id)
        ON DELETE CASCADE,

    -- Garante 1 seção por tipo por empreendimento
    CONSTRAINT uq_landing_section_business_type
        UNIQUE (business_id, type)
);

CREATE INDEX IF NOT EXISTS idx_landing_section_business
    ON landing_section (business_id);

CREATE INDEX IF NOT EXISTS idx_landing_section_enabled
    ON landing_section (enabled);
