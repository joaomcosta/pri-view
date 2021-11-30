# Pri-View: Privacy-Preserving Views for Data Analysis and Publication

This repository contains the materials presented in the thesis [[1](#thesis)]. It includes instructions on how to load the Differential Private extension into PostgreSQL, as well the implementation of the proxy protoype.

## Abstract

Data is being generated and processed at an unprecedented scale. Statistical data analysis is in high demand, with many organizations using it for a broad range of interests, from researching to guiding business decisions. However, this massive generation of data raises privacy concerns, as most of this data contains sensitive information about individuals. In turn, several regulations have emerged to give people more control over their data, such as the European General Data Protection Regulation.

For organizations, the challenge is how to analyze and publish data without compromising an individual's privacy. In the context of Relational Databases, they still lack features for this, with solutions involving manually removing identifying information from the data or only allowing certain aggregate queries. However, these solutions can be susceptible to attacks and do not provide strong privacy guarantees.

In this thesis, we propose to explore a solution to address the challenge of privately analyzing and publishing data on Relational Databases. To this end, we present a new type of Views - privacy-preserving views - which allow for computing statistical aggregations on data while preserving privacy. We focus our studies on Differential Privacy, a recent mathematical definition of privacy, and explore how to turn common aggregation functions into their private counterparts.

We present our solution in two parts. In the first part, we present a solution to create privacy-preserving views for a specific database, namely PostgreSQL. In the second part, we present the design and implementation of a database proxy, which supports any SQL database and produces private statistical results. The experimental results show that our proposed solutions can achieve balanced performance - views containing count functions perform better than views containing other functions. They also show that both solutions are capable of providing accurate privacy-preserving data for large databases and sample sizes.

## Repository Structure

    .
    ├── Chorus                  # Chorus framework fork
    ├── PrivProxy               # Proxy project
    └── README.md

* __Chorus__: contains the Chorus project with the designed Differential Private Mechanisms ([Mechanisms](Chorus/src/main/scala/chorus/mechanisms) folder).
* __PrivProxy__: contains the implementation of the Proxy prototype.

## Instructions to build the Differential Privacy PostgreSQL extension


Update package lists, install dependencies - build-essentials (gcc, etc), wget, libreadline-dev, zlib, bison, flex, git, openjdk-8-jdk, maven.
```bash
apt-get update
apt-get install build-essential wget libreadline-dev zlib1g-dev bison flex git openjdk-8-jdk maven
```

Download, build, and install a Postgres from source
```bash
wget https://ftp.postgresql.org/pub/source/v11.0/postgresql-11.0.tar.gz
tar zxvf postgresql-11.0.tar.gz
cd postgresql-11.0/
./configure
make
make install
```

Verify postgres directory structure and add pgsql to system path
```bash
ls -l /usr/local/pgsql/
PATH=/usr/local/pgsql/bin:$PATH
export PATH
```

Create postgreSQL user account (pass: postgres)
```bash
adduser postgres
```

Create postgreSQL data directory, make postgres user the owner
```bash
mkdir /usr/local/pgsql/data
chown postgres:postgres /usr/local/pgsql/data
ls -ld /usr/local/pgsql/data
```

Initialize postgreSQL data directory and validate
```bash
su - postgres
/usr/local/pgsql/bin/initdb -D /usr/local/pgsql/data/
ls -l /usr/local/pgsql/data
```

Start a new instance of Postgres
```bash
/usr/local/pgsql/bin/postmaster -D /usr/local/pgsql/data >logfile 2>&1 &
```

Create test DB 
```bash
/usr/local/pgsql/bin/createdb test
/usr/local/pgsql/bin/psql test
```

Build and load Google-DifPriv postgres extension

Install Bazel tool, version 4.1.0 (google's software manager)
```bash
apt-get install apt-transport-https curl gnupg
curl -fsSL https://bazel.build/bazel-release.pub.gpg | gpg --dearmor > bazel.gpg
mv bazel.gpg /etc/apt/trusted.gpg.d/
echo "deb [arch=amd64] https://storage.googleapis.com/bazel-apt stable jdk1.8" | tee /etc/apt/sources.list.d/bazel.list
apt-get update && apt-get install bazel-4.1.0
ln -s /usr/bin/bazel-4.1.0 /usr/bin/bazel
bazel --version
```


Download library, build and install cc postgres extension.
- Make sure pgsql is in path AND run script form cc directory). 
- Note: script calls sudo command, delete from script if needed.
```bash
git clone https://github.com/google/differential-privacy.git
cd differential-privacy/cc
PATH=/usr/local/pgsql/bin:$PATH
export PATH
./postgres/install_extension.sh
```

Start Postgres instance and load extension
```bash
CREATE EXTENSION anon_func;
```

## Testing the Proxy prototype

* Setup the proxy using the configurations files (_connection_, _schema_ and _views_ in [Resources](PrivProxy/src/main/resources/) folder).
* Execute the following command in the [Proxy](PrivProxy/) folder to start the application.
```bash
    mvn spring-boot:run
```

## Other useful links
* [TPC-H Database generator for PostgreSQL](https://github.com/joaomcosta/pg-tpch-dbgen)
* [Google's differential privacy libraries](https://github.com/google/differential-privacy)
* [Chorus Framework](https://github.com/uvm-plaid/chorus)

## References
<a name="thesis">1.</a> João Costa. Pri-View: Privacy-Preserving Views for Data Analysis and Publication, MSc Thesis. NOVA School of Science and Technology | FCT NOVA, 2021.