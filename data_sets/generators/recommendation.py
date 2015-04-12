import random
import os
import json

CWD = os.path.dirname(os.path.realpath(__file__))

def generate(people_count, total_interests, total_places_of_work, max_interests, max_places_of_work):

    # Complete the full path name of the file
    full_file_path = os.path.join(CWD, '../recommendation.json')

    if os.path.isfile(full_file_path):
        # The file already exists: skip
        print('File "{}" already exists...skipped.'.format(full_file_path))
    else:

        # Produce output for user
        print('Generating recommendation data set...')
        print('  + People:                 {}'.format(people_count))
        print('  + Interests:              {}'.format(total_interests))
        print('  + Places of work:         {}'.format(total_places_of_work))
        print('  + Maximum interests:      {}'.format(max_interests))
        print('  + Maximum places of work: {}'.format(max_places_of_work))

        with open(full_file_path, 'w') as generated_file:
            # Create the range of people, interests, and places of work IDs
            people_ids = range(0, people_count)
            interests_ids = range(0, max_interests)
            places_of_work_ids = range(0, max_places_of_work)

            # Add the opening tag to the JSON output
            json_entry = {'people': []}

            for person_idx in people_ids:
                # Add entry for each person
                json_entry['people'].append({
                    'id': person_idx,
                    'plcs_wrk': random.sample(xrange(total_places_of_work), max_places_of_work),
                    'interests': random.sample(xrange(total_interests), max_interests)
                })

                # Add the number of interests and places of work
                json_entry['interests_count'] = total_interests
                json_entry['plcs_wrk_count'] = total_places_of_work

            # Write the JSON entry to the file
            generated_file.write(json.dumps(json_entry))

        # Writng completed
        print('done.')


if __name__ == '__main__':
    generate(1000, 1000, 100, 10, 5)