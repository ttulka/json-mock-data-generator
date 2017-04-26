package cz.net21.ttulka.json.mock.generator.model;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Simple configuration node.
 * 
 * @author ttulka	
 *
 */
public class Node {

	private NodeTypes type;
	private String name;
	private String value;
	private List<String> values;
	private int repeatMin = 1;
	private int repeatMax = 1;
	private Integer min;
	private Integer max;
	private Date minDate;
	private Date maxDate;
	private Path path;
	
	private List<Node> children;
	
	public Node(NodeTypes type, String name) {
		this();
		this.type = type;
		this.name = name;
	}
	
	private Node() {
		super();
	}

	public static NodeBuilder builder(NodeTypes type, String name) {
		NodeBuilder builder = new NodeBuilder();
		builder.type(type).name(name);
		return builder;
	}
	
	public NodeTypes getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public List<String> getValues() {
		return values;
	}

	public Integer getMin() {
		return min;
	}

	public Integer getMax() {
		return max;
	}

	public Date getMinDate() {
		return minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public int getRepeatMin() {
		return repeatMin;
	}

	public int getRepeatMax() {
		return repeatMax;
	}

	public Path getPath() {
		return path;
	}

	public List<Node> getChildren() {
		return children;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Node [type=" + type + ", name=" + name + "]";
	}

	static class NodeBuilder {
		
		private Node node;
		
		private NodeBuilder() {
			node = new Node();
		}
		
		public Node build() {
			return node;
		}
		
		private NodeBuilder type(NodeTypes type) {
			node.type = type;
			return this;
		}
		
		private NodeBuilder name(String name) {
			node.name = name;
			return this;
		}
		
		NodeBuilder value(String value) {
			node.value = value;
			return this;
		}
		
		NodeBuilder values(String values) {
			node.values = Arrays.stream(values.split(",")).map(String::trim).collect(Collectors.toList());
			return this;
		}
		
		NodeBuilder repeat(String repeat) {
			String[] split = repeat.split(",");			
			node.repeatMin = parseInt(split[0].trim());
			if (split.length > 1) {
				node.repeatMax = parseInt(split[1].trim());
			} else {
				node.repeatMax = node.repeatMin;
			}
			return this;
		}
		
		NodeBuilder path(Path path) {
			node.path = path;
			return this;	
		}
		
		NodeBuilder min(String min) {
			Optional<Date> date = getAsDate(min);
			if (date.isPresent()) {
				node.minDate = date.get();
			} else {
				node.min = parseInt(min);
			}
			return this;
		}
		
		NodeBuilder max(String max) {
			Optional<Date> date = getAsDate(max);
			if (date.isPresent()) {
				node.maxDate = date.get();
			} else {
				node.max = parseInt(max);
			}
			return this;
		}
		
		NodeBuilder children(List<Node> children) {
			node.children = children;
			return this;
		}
		
		private int parseInt(String str) {
			try {
				int val = Integer.parseInt(str);
				return val >= 0 ? val : 0;
			} 
			catch (Exception e) {
				return 0;
			}
		}
		
		private Optional<Date> getAsDate(String str) {
			try {
				if (Pattern.compile("\\d{4}-\\d{2}-\\d{2}").matcher(str).matches()) {
					return Optional.of(new SimpleDateFormat("yyyy-MM-dd").parse(str));
				}
				if (Pattern.compile("\\d{1,2}.\\d{1,2}.\\d{4}").matcher(str).matches()) {
					return Optional.of(new SimpleDateFormat("dd.MM.yyyy").parse(str));
				}
			} 
			catch (Exception e) {
				throw new IllegalArgumentException("Wrong date format: " + str);
			}
			return Optional.empty();
		}
	}	
}
