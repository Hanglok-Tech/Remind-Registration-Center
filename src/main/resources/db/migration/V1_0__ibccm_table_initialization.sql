CREATE TABLE IF NOT EXISTS registration_info (
    id SERIAL PRIMARY KEY,
    remind_type TEXT NOT NULL,
    remind_sub_type TEXT NULL,
    device_name TEXT NOT NULL,
    remind_uuid TEXT NOT NULL,
    service_ip TEXT NOT NULL,
    service_port INTEGER NULL,
    status TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by TEXT NOT NULL DEFAULT 'remind-scheduler',
    updated_by TEXT NOT NULL DEFAULT 'remind-scheduler'
);

CREATE INDEX if not exists registration_info_remind_type_idx ON registration_info(remind_type);
CREATE INDEX if not exists registration_info_remind_uuid_idx ON registration_info(remind_uuid);

