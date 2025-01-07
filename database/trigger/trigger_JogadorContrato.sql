/*trigger_JogadorContrato : Este trigger tem como objetivo impedir que um atleta esteja no ativo em mais que um clube */
CREATE OR ALTER TRIGGER trigger_JogadorContrato
ON Jogador_Contrato
AFTER INSERT, UPDATE
AS 
BEGIN
DECLARE		@v_nr_atleta INTEGER, 
			@v_data_entrada_atleta DATE, 
			@v_data_saida_atleta DATE,
			@v_cont INTEGER,
			@v_exist_contr_end_date_null INT,
			@v_error INTEGER,
			@v_error_msg NVARCHAR(1000) ;

			SELECT @v_nr_atleta =nr_atleta,
			@v_data_entrada_atleta=data_entrada_atleta,  
			@v_data_saida_atleta=data_saida_atleta    
			FROM 
			inserted;

	BEGIN TRY
		--
		if (@v_data_entrada_atleta > @v_data_saida_atleta )
		begin
		    --
			set @v_error =54000;
			set @v_error_msg ='ERRO: A data de entrada não pode ser superior à data de saida'  ;
			ROLLBACK TRANSACTION;
			THROW 51000, '', 1
		end
		--
		select @v_exist_contr_end_date_null=count(1) 
		from Jogador_Contrato JC where JC.nr_atleta= @v_nr_atleta and JC.data_saida_atleta is not null;

		if (@v_exist_contr_end_date_null > 0)
		begin
		    --
			set @v_error =53000;
			set @v_error_msg ='ERRO: O atleta já tem contrato ativo, sem data fim'  ;
			ROLLBACK TRANSACTION;
			THROW 51000, '', 1
		end

		--
		SET @v_cont=0;
		--
		select @v_cont=count(1)
		from Jogador_Contrato JC
		where JC.nr_atleta= @v_nr_atleta and
		@v_data_entrada_atleta between JC.data_entrada_atleta and jc.data_saida_atleta;
		--
		IF @v_cont >1 BEGIN
 			set @v_error =52000;
			set @v_error_msg ='ERRO: O atleta já tem contrato ativo'  ;
			ROLLBACK TRANSACTION;
			THROW 51000, '', 1
		END
	END TRY
	BEGIN CATCH
		THROW @v_error,@v_error_msg, 1		
	END CATCH;
END
GO