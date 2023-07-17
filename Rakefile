# frozen_string_literal: true

require 'rake_leiningen'
require 'rake_terraform'
require 'rake_vault'
require 'vault'
require 'yaml'
require 'rake_fly'

require_relative 'lib/leiningen_task_set'

task default: %i[library:check library:test:unit]

RakeFly.define_installation_tasks(version: '7.9.0')

RakeLeiningen.define_installation_tasks(
  version: '2.10.0'
)

RakeVault.define_installation_tasks(
  path: File.join(Dir.pwd, 'vendor', 'vault'),
  version: '1.11.2'
)

namespace :vault do
  RakeVault.define_login_task(
    argument_names: [:role, :address],
  ) do |t, args|
    t.address = args[:address]
    t.role = args[:role] || 'read-only'
  end
end

namespace :secrets do
  task :provision, [:vault_address] do |t, args|
    Rake::Task["vault:login"].invoke('kv-admin', args[:vault_address])
    provision_secrets(args[:vault_address])
  end
end

namespace :library do
  define_check_tasks(fix: true)

  namespace :test do
    RakeLeiningen.define_test_task(
      name: :unit,
      type: 'unit',
      profile: 'test')
  end

  namespace :publish do

    task :prepare, [:vault_address] do |t, args|
      Rake::Task["vault:login"].invoke('read-only', args[:vault_address])
      content = vault_client.kv('kv').read("#{vault_base_path}/clojars-deploy-gpg")
      File.write(File.expand_path('~/.lein/credentials.clj.gpg'), content)
    end

    RakeLeiningen.define_release_task(
      name: :prerelease,
      profile: 'prerelease')

    RakeLeiningen.define_release_task(
      name: :release,
      profile: 'release')  do |t|
          t.environment = {
              'VERSION' => ENV['VERSION'],
              'CLOJARS_DEPLOY_USERNAME' => ENV['CLOJARS_DEPLOY_USERNAME'],
              'CLOJARS_DEPLOY_TOKEN' => ENV['CLOJARS_DEPLOY_TOKEN']
          }
          end
  end

  desc 'Lint all src files'
  task :lint do
    puts "Running clj-kondo from ./scripts/lint for " + RUBY_PLATFORM
    platform_prefix = /darwin/ =~ RUBY_PLATFORM ? "mac" : "linux"

    sh("./scripts/lint/clj-kondo-2021-06-18-#{platform_prefix}",
       "--lint", "src/")

    puts "Finished running clj-kondo"
  end

  desc 'Reformat all src files'
  task :format do
    puts "Running cljstyle from ./scripts/lint for " + RUBY_PLATFORM
    platform_prefix = /darwin/ =~ RUBY_PLATFORM ? "mac" : "linux"

    sh("./scripts/lint/cljstyle-0-15-0-#{platform_prefix}", "fix")

    puts "Finished running cljstyle"
  end

  task :optimise do
    puts 'skipping optimise...'
  end
  task :idiomise do
    puts 'skipping idiomise...'
  end
end

namespace :ci do
  RakeFly.define_project_tasks(
    pipeline: 'placid-fish',
    argument_names: [:concourse_url],
    backend: RakeFly::Tasks::Authentication::Login::FlyBackend
  ) do |t, args|

    t.concourse_url = args[:concourse_url]
    t.config = "pipelines/pipeline.yaml"
    t.non_interactive = true
    t.home_directory = 'build/fly'
  end
end

def provision_secrets(vault_address)
  token = File.read(File.expand_path('~/.vault-token'))
  vault_client = Vault::Client.new(address: vault_address, token: token)
  vault_base_path = 'placid-fish'

  puts 'Enter value for clojars_deploy_username:'
  deploy_username = STDIN.gets.chomp

  vault_client.kv('kv').write("#{vault_base_path}/clojars_deploy_username", value: deploy_username)

  puts 'Enter value for clojars-deploy-token:'
  deploy_token = STDIN.gets.chomp

  vault_client.kv('kv').write("#{vault_base_path}/clojars-deploy-token", value: deploy_token)

  puts "Enter path to file for clojars-deploy-gpg:"
  path = STDIN.gets.chomp
  content = File.read(path)
  if content.empty?
    raise :file_is_empty
  end

  vault_client.kv('kv').write("#{vault_base_path}/clojars-deploy-gpg", value: Base64.strict_encode64(content))
end
