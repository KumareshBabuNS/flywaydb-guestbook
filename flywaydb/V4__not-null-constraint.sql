UPDATE message SET company='' WHERE company IS NULL;
ALTER TABLE message MODIFY company VARCHAR(25) NOT NULL;
