
CREATE TABLE optimization_response (
       request_id VARCHAR(255) PRIMARY KEY,
       total_volume FLOAT8 NOT NULL,
       total_revenue FLOAT8 NOT NULL,
       created_at TIMESTAMP
);

CREATE TABLE shipment (
      id BIGSERIAL PRIMARY KEY,
      name VARCHAR(255),
      volume FLOAT8 NOT NULL,
      revenue FLOAT8 NOT NULL
);


CREATE TABLE optimization_response_selected_shipments (
      optimization_response_request_id VARCHAR(255) NOT NULL,
      selected_shipments_id BIGINT NOT NULL UNIQUE,
      CONSTRAINT fk_response FOREIGN KEY (optimization_response_request_id) REFERENCES optimization_response (request_id),
      CONSTRAINT fk_shipment FOREIGN KEY (selected_shipments_id) REFERENCES shipment (id)
);