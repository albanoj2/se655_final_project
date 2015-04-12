import random
import os
import json

CWD = os.path.dirname(os.path.realpath(__file__))

def generate(node_count, file_name, max_connections):

    # Complete the full path name of the file
    full_file_path = '{}/{}/{}'.format(CWD, '../social_network/', file_name)

    if os.path.isfile(full_file_path):
        # The file already exists: skip
        print('File "{}" already exists...skipped.'.format(file_name))
    else:

        # Produce output for user
        print('Generating data set with {} nodes (maximum connections: {}) to "{}"...'.format(node_count, max_connections, file_name)),

        with open(full_file_path, 'w') as generated_file:
            # Create the range of node IDs
            node_ids = range(0, node_count)

            # Add the opening tag to the JSON output
            json_entry = []

            for node_idx in node_ids:
                # Create a list of the relationships for this node
                relationships = []

                for relationship_idx in range(0, random.randint(0, max_connections)):
                    # Select a random number of relationships to create
                    relationships.append(node_ids[random.randint(0, node_count - 1)])

                # Create the JSON object to dump
                json_entry.append(relationships)

            # Write the JSON entry to the file
            generated_file.write(json.dumps(json_entry))

        # Writng completed
        print('done.')


if __name__ == '__main__':
    generate(1000, 'small.json', 20)
    generate(10000, 'medium.json', 20)
    generate(100000, 'large.json', 20)
    generate(1000000, 'very_large.json', 20)