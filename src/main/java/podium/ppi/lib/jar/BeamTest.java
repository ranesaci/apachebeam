package podium.ppi.lib.jar;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.beam.runners.flink.FlinkRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult.State;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.TupleTag;

/**
 * @author sachin
 * @date 18-Nov-2021
 */

public class BeamTest {
	static List<Stock> stocks = new ArrayList<>();

	public static void main(String[] args) {
		System.setProperty("java.specification.version", "1.8");
		process();
		// read();

	}

	public static void process() {
		final TupleTag<Stock> VALID = new TupleTag<Stock>() {
		};
		final TupleTag<Stock> INVALID = new TupleTag<Stock>() {
		};

		PipelineOptions options = PipelineOptionsFactory.create();

		options.setRunner(FlinkRunner.class);
		Pipeline p = Pipeline.create(options);

		// Preparing dummy data
		Collection<Stock> stockList = Arrays.asList(
				new Stock("AAP", 2000, "Apple Inc"),
				new Stock("MSF", 3000, "Microsoft Corporation"), 
				new Stock("NVDA", 4000, "NVIDIA Corporation"),
				new Stock("INT", 3200, "Intel Corporation"));

		// Reading dummy data and save it into PCollection<Stock>
		PCollection<Stock> data = p.apply(Create.of(stockList).withCoder(SerializableCoder.of(Stock.class)));
		// insert
		PCollectionTuple pCollectionTupleResult = data.apply("write", CustomJdbcIOWrite.<Stock>write()

						.withDataSourceConfiguration(CustomJdbcIOWrite.DataSourceConfiguration
						.create("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/postgres")
						.withUsername("postgres").withPassword("sachin"))
						.withStatement("insert into stocks values(?, ?, ?)").withValidTag(VALID).withInValidTag(INVALID)
						.withPreparedStatementSetter(new CustomJdbcIOWrite.PreparedStatementSetter<Stock>() {
							private static final long serialVersionUID = 1L;
		
							public void setParameters(Stock element, PreparedStatement query) throws SQLException {
								query.setString(1, element.getSymbol());
								query.setLong(2, element.getPrice());
								query.setString(3, element.getCompany());
							}
	
						}));
		// get failed PCollection using INVALID tupletag
		PCollection<Stock> failedPcollection = pCollectionTupleResult.get(INVALID)
				.setCoder(SerializableCoder.of(Stock.class));
			
		failedPcollection.apply(ParDo.of(new DoFn<Stock, Stock>() {

			private static final long serialVersionUID = 1L;

			@ProcessElement
			public void process(ProcessContext pc) {
				System.out.println("Failed pCollection element:" + pc.element().getCompany());
			}

		}));

		//get failed PCollection using INVALID tupletag
		PCollection<Stock> insertedPcollection = pCollectionTupleResult.get(VALID)
				.setCoder(SerializableCoder.of(Stock.class));
		insertedPcollection.apply(ParDo.of(new DoFn<Stock, Stock>() {

			private static final long serialVersionUID = 1L;
			
			@ProcessElement
			public void process(ProcessContext pc) {
				System.out.println("Inserted pCollection element:" + pc.element().getCompany());
			}
			
		}));
		
		// run pipeline
		State state = p.run().waitUntilFinish();
		System.out.println("Data inserted successfully with state : " + state);

	}

}
